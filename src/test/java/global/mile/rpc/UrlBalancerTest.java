package global.mile.rpc;

import global.mile.Config;
import global.mile.MileTestCase;
import global.mile.errors.WebWalletCallException;

public class UrlBalancerTest extends MileTestCase {

    public void testGetUrl() {
        Config config;
        try {
             config = Config.custom()
                    .setUseBalancing(true)
                    .setWebWalletUrl("https://wallet.testnet.mile.global")
                    .fetchApiUrlsFromWebWallet()
                    .build();
        } catch (WebWalletCallException e) {
            fail();
            return;
        }
        UrlBalancer b = new UrlBalancer(config.getApiUrls(), true);
        for (int i=0; i<10; i++) {
            System.out.println(b.getUrl());
        }

    }

}