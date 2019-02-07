package global.mile.transactions;

import global.mile.TestCase;
import global.mile.errors.MileException;
import global.mile.wallet.Wallet;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class EmissionTest extends TestCase {
    public void testEmission() {
        Wallet w1 = new Wallet("secret-phrase");
        System.out.println(w1.getPublicKey() + " " + w1.getPrivateKey());

        try {
            System.out.println(w1.getState().getBalances());
        } catch (MileException e) {
            fail(e.getMessage());
        }

        try {
            w1.emission(0, new BigDecimal("0.00"));
        } catch (MileException e) {
            fail(e.getMessage());
        }

        try {
            TimeUnit.SECONDS.sleep(42);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(w1.getState().getBalances());
        } catch (MileException e) {
            fail(e.getMessage());
        }
    }

}