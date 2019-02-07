package global.mile.wallet;

import global.mile.MileTestCase;

import java.math.BigDecimal;

public class AssetTest extends MileTestCase {

    public void testNormalizePrecision() {
        assertEquals("1.11", Asset.normalizePrecision(new BigDecimal("1.115"), Asset.XDR).toString());
        assertEquals("1.00", Asset.normalizePrecision(new BigDecimal("1"), Asset.XDR).toString());
        assertEquals("1.11500", Asset.normalizePrecision(new BigDecimal("1.115"), Asset.MILE).toString());
    }
}