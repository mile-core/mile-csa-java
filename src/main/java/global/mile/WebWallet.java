package global.mile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import global.mile.errors.WebWalletCallException;
import global.mile.rpc.SimpleHttpClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WebWallet {
    private static final String path = "/v1/nodes.json";

    public static List<String> fetchApiUrls(String webWalletUrl) throws WebWalletCallException {
        try {
            String res = SimpleHttpClient.get(webWalletUrl + path);
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            return new Gson().fromJson(res, listType);
        } catch (Exception e) {
            throw new WebWalletCallException(
                    "Error during fetching " + webWalletUrl + path + ": " + e.getMessage()
            );
        }
    }
}
