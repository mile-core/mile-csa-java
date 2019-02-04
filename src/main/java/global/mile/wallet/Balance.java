package global.mile.wallet;

import java.math.BigDecimal;

public class Balance {
    private Asset asset;
    private BigDecimal amount;

    public Balance(Asset asset, BigDecimal amount) {
        this.asset = asset;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Asset getAsset() {
        return asset;
    }

    @Override
    public String toString() {
        return asset.getName() + ": " + amount.toString();
    }
}
