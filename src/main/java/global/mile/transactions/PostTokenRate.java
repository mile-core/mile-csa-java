package global.mile.transactions;

import global.mile.Dict;
import global.mile.Wallet;
import global.mile.errors.ApiCallException;
import global.mile.wallet.Asset;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;

public class PostTokenRate extends TransactionWithFee {

    private final BigDecimal rate;

    public PostTokenRate(Wallet wallet, BigDecimal rate, @Nullable BigDecimal fee) throws ApiCallException {
        super(wallet, fee);

        this.rate = rate;
    }

    public PostTokenRate(Wallet wallet, BigDecimal rate) throws ApiCallException {
        this(wallet, rate, null);
    }

    @Override
    public String getName() {
        return "PostTokenRate";
    }

    @Override
    protected void doBuild(Dict data, SHA3.DigestSHA3 digest) {

        String rateStr = Asset.normalizePrecision(rate, Asset.XDR).toString();

        data.put("public-key", wallet.getPublicKey());
        data.put("asset", Map.of("code", Asset.XDR, "amount", rateStr));
        data.put("fee", fee.toString());

        //digest

        digest.update(wallet.getPublicKeyBytes());
        digest.update(rateStr.getBytes());

    }
}
