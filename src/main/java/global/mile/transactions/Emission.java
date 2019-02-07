package global.mile.transactions;

import global.mile.Dict;
import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;
import global.mile.wallet.Wallet;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class Emission extends TransactionWithFee {

    private final int assetCode;

    public Emission(Wallet wallet, int assetCode,
                    @Nullable BigDecimal fee, @Nullable BigInteger id, @Nullable BigInteger blockId)
            throws WebWalletCallException, ApiCallException {
        super(wallet, fee, id, blockId);
        this.assetCode = assetCode;
    }

    public Emission(Wallet wallet, int assetCode, BigDecimal fee) throws WebWalletCallException, ApiCallException {
        this(wallet, assetCode, fee, null, null);
    }

    public Emission(Wallet wallet, int assetCode) throws WebWalletCallException, ApiCallException {
        this(wallet, assetCode, null);
    }

    @Override
    public String getName() {
        return "EmissionTransaction";
    }

    @Override
    protected void doBuild(Dict data, SHA3.DigestSHA3 digest) {

        data.put("from", wallet.getPublicKey());
        data.put("asset", Map.of("code", assetCode));
        data.put("fee", fee.toString());

        //digest

        digest.update(Digest.prepareAssetCode(assetCode));
        digest.update(wallet.getPublicKeyBytes());
    }
}
