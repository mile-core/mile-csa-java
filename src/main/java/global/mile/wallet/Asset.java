package global.mile.wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class Asset {
    public static final int XDR = 0;
    public static final int MILE = 1;

    public static final Map<Integer, Integer> PRECISIONS = Map.of(XDR, 2, MILE, 5);

    private String name;
    private int code;

    public Asset(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static BigDecimal normalizePrecision(BigDecimal amount, int assetCode) {
        return amount.setScale(PRECISIONS.get(assetCode), RoundingMode.DOWN);
    }
}
