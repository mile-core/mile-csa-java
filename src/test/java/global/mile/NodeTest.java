package global.mile;

import global.mile.errors.MileException;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class NodeTest extends MileTestCase {

    public void testRegister() {
        Chain chain = new Chain(config);

        Wallet w1 = new Wallet("secret-phrase");
        System.out.println(w1.getPublicKey() + " " + w1.getPrivateKey());

        try {
            System.out.println(w1.getState(chain).getBalances());
        } catch (MileException e) {
            fail(e.getMessage());
        }

        Node node = new Node(w1, "mile.global");


        try {
            System.out.println("Started registration");
            node.register(chain, new BigDecimal("10000"));
            TimeUnit.SECONDS.sleep(42);
//            node.postTokenRate(chain, new BigDecimal("1.33"));
//            TimeUnit.SECONDS.sleep(42);
            System.out.println("Started unregistration");
            node.unregister(chain);
            TimeUnit.SECONDS.sleep(0);

        } catch (MileException | InterruptedException e) {
            if (e.getMessage().contains("not enough tokens")) {
                System.out.println(e.getMessage());
            } else {
                fail(e.getMessage());
            }
        }
    }

}