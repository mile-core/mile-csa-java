package global.mile.rpc;

import global.mile.Dict;
import global.mile.MileTestCase;
import global.mile.errors.MileException;

public class GetInfoTest extends MileTestCase {

    public void testExec() {
        RpcFactory rpcFactory = new RpcFactory(config);

        GetInfo i = rpcFactory.createGetInfo("get-blockchain-state");
        try {
            System.out.println(i.exec());
        } catch (MileException e) {
            fail();
        }
    }
}