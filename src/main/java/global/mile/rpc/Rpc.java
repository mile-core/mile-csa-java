package global.mile.rpc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import global.mile.Config;
import global.mile.Dict;
import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.*;

public class Rpc
{
    private BigInteger id;
    private Payload payload;

    private static String path = "/v" + Config.version + "/api";

    private static List<String> urls;
    private static int currentUrlIndex = 0;

    public Rpc(String method, Dict params) {
        id = new BigInteger("0");

        payload = new Payload();
        payload.method = method;
        payload.id = id;
        payload.params = params;

    }

    public static void refreshNodesUrls() throws WebWalletCallException {
        if (urls == null) {
            try {
                String res = SimpleHttpClient.get(Config.Web.nodesUrls());
                Type listType = new TypeToken<ArrayList<String>>(){}.getType();
                urls = new Gson().fromJson(res, listType);
            } catch (Exception e) {
                throw new WebWalletCallException(
                        "Error during fetching " + Config.Web.nodesUrls() + ": " + e.getMessage()
                );
            }

            if (urls.size() == 0) {
                throw new WebWalletCallException("Empty list of nodes urls: " + Config.Web.nodesUrls());
            }
        }
    }

    public static String getUrl() throws WebWalletCallException {
        if (Config.useBalancing) {
            refreshNodesUrls();

            return urls.get(currentUrlIndex++ % urls.size()) + path;
        } else {
            return Config.url + path;
        }
    }

    protected Dict doExec() throws ApiCallException, WebWalletCallException {
        this.id = id.add(new BigInteger("1"));

        payload.id = this.id;
        Gson gson = new Gson();

        String url = getUrl();
        Dict response;

        try {
            String res = SimpleHttpClient.postJson(url, gson.toJson(payload));
            response =  gson.fromJson(res, new TypeToken<Dict>(){}.getType());
        } catch (Exception e) {
            throw new ApiCallException("Error during fetching " + url + ": " + e.getMessage());
        }

        Object error = response.get("error");
        if (error!=null) {
            String message = "Unknown error";
            if (error instanceof Map<?, ?> && ((Map<?, ?>) error).get("message") instanceof String) {
                message = (String) ((Map<?, ?>) error).get("message");
            }

            throw new ApiCallException(message);
        }

        return response;
    }


    /////////////////////////////////////////////////////

    class Payload {
        String method;
        Map<String, Object> params;
        String jsonrpc = "2.0";
        BigInteger id;

    }
}
