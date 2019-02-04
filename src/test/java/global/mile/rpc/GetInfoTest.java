package global.mile.rpc;

import global.mile.Dict;
import global.mile.TestCase;
import global.mile.errors.MileException;

public class GetInfoTest extends TestCase {

    public void testExec() {
        GetInfo i = new GetInfo("get-blockchain-state", new Dict());
        try {
            System.out.println(i.exec());
        } catch (MileException e) {
            fail();
        }
    }
}