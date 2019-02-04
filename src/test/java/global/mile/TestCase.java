package global.mile;

public abstract class TestCase extends junit.framework.TestCase {

    public TestCase() {
        super();
    }

    public TestCase(String name) {
        super(name);
    }

    protected void setUp() throws Exception {

        Config.Web.url = "https://wallet.testnet.mile.global";
    }
}
