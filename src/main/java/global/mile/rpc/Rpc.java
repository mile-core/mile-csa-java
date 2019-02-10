package global.mile.rpc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import global.mile.Dict;
import global.mile.errors.ApiCallException;

import java.math.BigInteger;
import java.util.Map;

public abstract class Rpc
{
    private String apiVersion;
    private UrlBalancer urlBalancer;

    private BigInteger id;
    private Payload payload;

    public Rpc(String method, Dict params, String apiVersion, UrlBalancer urlBalancer) {
        this.apiVersion = apiVersion;
        this.urlBalancer = urlBalancer;

        id = new BigInteger("0");

        payload = new Payload();
        payload.method = method;
        payload.id = id;
        payload.params = params;
    }

    private String getPath() {
        return "/v" + apiVersion + "/api";
    }

    public String getUrl() {
        return urlBalancer.getUrl() + getPath();
    }

    protected Dict execInternal() throws ApiCallException {
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
