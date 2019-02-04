package global.mile.transactions;

import global.mile.TestCase;
import global.mile.errors.MileException;
import global.mile.wallet.Wallet;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class TransferTest extends TestCase {
    public void testTransfer() {
        Wallet w1 = new Wallet("destination-secret-phrase");
        System.out.println(w1.getPublicKey() + " " + w1.getPrivateKey());

        Wallet w2 = new Wallet("secret-phrase");
        System.out.println(w2.getPublicKey() + " " + w2.getPrivateKey());
        try {
            System.out.println(w2.getState().getBalances());
        } catch (MileException e) {
            fail(e.getMessage());
        }

        try {
            w1.transfer(w2, 0, new BigDecimal("0.01"), "transfer test", new BigDecimal("0.00"));
        } catch (MileException e) {
            fail(e.getMessage());
        }

        try {
            TimeUnit.SECONDS.sleep(42);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(w2.getState().getBalances());
        } catch (MileException e) {
            fail(e.getMessage());
        }
    }

}