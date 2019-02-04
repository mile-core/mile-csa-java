package global.mile.rpc;

import global.mile.TestCase;
import global.mile.errors.WebWalletCallException;

public class RpcTest extends TestCase {

    public void testGetUrl() {
        //Rpc r = new Rpc("", new Rpc.PayloadParams());
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