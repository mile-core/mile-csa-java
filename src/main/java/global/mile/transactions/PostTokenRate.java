package global.mile.transactions;

import global.mile.Dict;
import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;
import global.mile.wallet.Asset;
import global.mile.wallet.Wallet;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class PostTokenRate extends TransactionWithFee {

    private final BigDecimal rate;

    public PostTokenRate(Wallet wallet, BigDecimal rate,
                         @Nullable BigDecimal fee, @Nullable BigInteger id, @Nullable BigInteger blockId)
            throws WebWalletCallException, ApiCallException {
        super(wallet, fee, id, blockId);

        this.rate = rate;
    }

    public PostTokenRate(Wallet wallet, BigDecimal rate, @Nullable BigDecimal fee) throws WebWalletCallException, ApiCallException {
        this(wallet, rate, fee, null, null);
    }

    public PostTokenRate(Wallet wallet, BigDecimal rate) throws WebWalletCallException, ApiCallException {
        this(wallet, rate, null);
    }

    @Override
    public String getName() {
        return "VotingCoursePoll";
    }

    @Override
    protected void doBuild(Dict data, SHA3.DigestSHA3 digest) {

        String rateStr = Asset.normalizePrecision(rate, Asset.XDR).toString();

        data.put("public-key", wallet.getPublicKey());
        data.put("asset", Map.of("code", Asset.XDR, "amount", rateStr));

        //digest

        digest.update(wallet.getPublicKeyBytes());
        digest.update(rateStr.getBytes());

    }
}
