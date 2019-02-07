package global.mile;

import global.mile.errors.MileException;
import global.mile.wallet.Wallet;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class NodeTest extends MileTestCase {

    public void testRegister() {
        Wallet w1 = new Wallet("secret-phrase");
        System.out.println(w1.getPublicKey() + " " + w1.getPrivateKey());

        try {
            System.out.println(w1.getState().getBalances());
        } catch (MileException e) {
            fail(e.getMessage());
        }

        Node node = new Node(w1, "mile.global");


        try {
            node.register(new BigDecimal("10000"));
            TimeUnit.SECONDS.sleep(42);
            node.postTokenRate(new BigDecimal("1.33"));
            TimeUnit.SECONDS.sleep(42);
            node.unregister();

        } catch (MileException | InterruptedException e) {
            fail(e.getMessage());
        }
    }

}