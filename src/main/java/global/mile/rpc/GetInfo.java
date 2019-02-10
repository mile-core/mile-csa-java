package global.mile.rpc;

import global.mile.Dict;
import global.mile.errors.ApiCallException;

import java.util.Map;

public class GetInfo extends Rpc {
    public GetInfo(String method, Dict params, String apiVersion, UrlBalancer urlBalancer) {
        super(method, params, apiVersion, urlBalancer);
    }

    public Dict exec() throws ApiCallException {
        Dict response = execInternal();
        Object result = response.get("result");
        if (result == null) {
            return null;
        }
        if (!(result instanceof Map<?, ?>)) {
            throw new ApiCallException("Invalid response");
        }

        Dict dict = new Dict();
        for (Map.Entry<?, ?> entry : ((Map<?, ?>) result).entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                throw new ApiCallException("Invalid response (key type)");
            }
            dict.put((String) entry.getKey(), entry.getValue());
        }

        return dict;
    }
}
