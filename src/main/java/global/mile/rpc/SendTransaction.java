package global.mile.rpc;

import global.mile.Dict;
import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;

public class SendTransaction extends Rpc {
    public SendTransaction(Dict params) {
        super("send-transaction", params);
    }

    public boolean exec() throws ApiCallException, WebWalletCallException {

        Dict response = doExec();
        Object result = response.get("result");
        if (result==null || !(result instanceof java.lang.String)) {
            throw new ApiCallException("Invalid response");
        }

        return result.equals("true");
    }
}
