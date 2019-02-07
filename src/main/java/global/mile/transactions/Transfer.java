package global.mile.transactions;

import global.mile.Dict;
import global.mile.crypto.PublicKey;
import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;
import global.mile.wallet.Asset;
import global.mile.wallet.Wallet;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class Transfer extends TransactionWithFee {

    private final int assetCode;
    private final BigDecimal amount;
    private final String destination;
    private final String description;

    public Transfer(Wallet wallet, int assetCode, BigDecimal amount, String destination, String description,
                    @Nullable BigDecimal fee, @Nullable BigInteger id, @Nullable BigInteger blockId)
            throws WebWalletCallException, ApiCallException {
        super(wallet, fee, id, blockId);
        this.assetCode = assetCode;
        this.amount = amount;
        this.destination = destination;
        this.description = description;
    }

    public Transfer(Wallet wallet, int assetCode, BigDecimal amount, String destination, String description,
                    @Nullable BigDecimal fee) throws WebWalletCallException, ApiCallException {
        this(wallet, assetCode, amount, destination, description, fee, null, null);
    }

    public Transfer(Wallet wallet, int assetCode, BigDecimal amount, String destination)
            throws WebWalletCallException, ApiCallException {
        this(wallet, assetCode, amount, destination, "", null);
    }


    @Override
    public String getName() {
        return "TransferAssetsTransaction";
    }

    @Override
    protected void doBuild(Dict data, SHA3.DigestSHA3 digest) {

        String amountStr = Asset.normalizePrecision(amount, assetCode).toString();

        data.put("from", wallet.getPublicKey());
        data.put("to", destination);
        data.put("asset", Map.of("code", assetCode, "amount", amountStr));
        data.put("description", description);

        data.put("fee", fee.toString());

        //digest

        digest.update(Digest.prepareAssetCode(assetCode));
        digest.update(wallet.getPublicKeyBytes());
        digest.update(PublicKey.fromBase58WithCheckSum(destination).getData());
        digest.update(amountStr.getBytes());
        digest.update(description.getBytes());

    }
}
