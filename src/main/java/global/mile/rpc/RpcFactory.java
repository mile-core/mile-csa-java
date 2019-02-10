package global.mile.rpc;

import global.mile.Config;
import global.mile.Dict;

public class RpcFactory {

    private Config config;
    private UrlBalancer urlBalancer;

    public RpcFactory(Config config) {
        this.config = config;
        this.urlBalancer = new UrlBalancer(config.getApiUrls(), config.isUseBalancing());
    }

    public GetInfo createGetInfo(String method, Dict params) {
        return new GetInfo(method, params, config.getApiVersion(), urlBalancer);
    }

    public GetInfo createGetInfo(String method) {
        return createGetInfo(method, new Dict());
    }

    public SendTransaction createSendTransaction(Dict params) {
        return new SendTransaction(params, config.getApiVersion(), urlBalancer);
    }
}
