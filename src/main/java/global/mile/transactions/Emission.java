package global.mile.transactions;

import global.mile.Dict;
import global.mile.Wallet;
import global.mile.errors.ApiCallException;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;

public class Emission extends TransactionWithFee {

    private final int assetCode;

    public Emission(Wallet wallet, int assetCode, @Nullable BigDecimal fee) throws ApiCallException {
        super(wallet, fee);
        this.assetCode = assetCode;
    }

    public Emission(Wallet wallet, int assetCode) throws ApiCallException {
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
