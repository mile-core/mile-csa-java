package global.mile.rpc;

import global.mile.MileTestCase;
import global.mile.errors.WebWalletCallException;

public class RpcTest extends MileTestCase {

    public void testGetUrl() {
        try {
            for (int i=0; i<10; i++) {
                System.out.println(Rpc.getUrl());
            }

        } catch (WebWalletCallException e) {
            e.printStackTrace();
            fail();
        }
    }

}