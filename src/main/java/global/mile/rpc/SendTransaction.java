package global.mile.rpc;

import global.mile.Dict;
import global.mile.errors.ApiCallException;

public class SendTransaction extends Rpc {
    public SendTransaction(Dict params, String apiVersion, UrlBalancer urlBalancer) {
        super("send-transaction", params, apiVersion, urlBalancer);
    }

    public boolean exec() throws ApiCallException {

        Dict response = execInternal();
        Object result = response.get("result");
        if (!(result instanceof String)) {
            throw new ApiCallException("Invalid response");
        }

        return result.equals("true");
    }
}
