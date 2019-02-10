package global.mile;

import global.mile.errors.ApiCallException;
import junit.framework.TestCase;

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
}