package global.mile;

import global.mile.errors.ApiCallException;
import global.mile.transactions.Transaction;

import java.math.BigInteger;

public class ChainTest extends MileTestCase {

    public void testGetCurrentBlockId() {
        Chain chain = new Chain(config);

        try {
            System.out.println(chain.getCurrentBlockId());
        } catch (ApiCallException e) {
            fail();
        }
    }

    public void testGetSupportedTransactions() {
        Chain chain = new Chain(config);

        try {
            System.out.println(chain.getSupportedTransactions());
        } catch (ApiCallException e) {
            fail();
        }
    }

    public void testGetBlock() {
        Chain chain = new Chain(Config.custom().build());

        BigInteger lastBlock;
        try {
            lastBlock = chain.getCurrentBlockId();
        } catch (ApiCallException e) {
            fail();
            return;
        }

        for (BigInteger i = lastBlock.subtract(new BigInteger("15")); i.compareTo(lastBlock) < 0; i = i.add(new BigInteger("1"))) {
            Block block;
            try {
                block = chain.getBlock(i);
            } catch (ApiCallException e) {
                fail();
                return;
            }
            System.out.println(block.getId() + " :: " + block.getTimestamp());
            for (Transaction t : block.getTransactions()) {
                System.out.println(t);
            }

        }
    }
}