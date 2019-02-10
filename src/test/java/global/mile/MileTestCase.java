package global.mile;

import java.awt.*;
import java.util.Arrays;

public abstract class MileTestCase extends junit.framework.TestCase {

    public MileTestCase() {
        super();
    }

    public MileTestCase(String name) {
        super(name);
    }

    protected Config config;

    protected void setUp() throws Exception {

        this.config = Config.custom()
                .setUseBalancing(false)
                .setApiUrls(Arrays.asList("https://lotus000.testnet.mile.global"))
                .build();
    }
}
