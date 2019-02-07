package global.mile;

public abstract class MileTestCase extends junit.framework.TestCase {

    public MileTestCase() {
        super();
    }

    public MileTestCase(String name) {
        super(name);
    }

    protected void setUp() throws Exception {

        Config.Web.url = "https://wallet.testnet.mile.global";
    }
}
