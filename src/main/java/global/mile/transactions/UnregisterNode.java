package global.mile.transactions;

import global.mile.Dict;
import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;
import global.mile.wallet.Wallet;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class UnregisterNode extends TransactionWithFee {


    public UnregisterNode(Wallet wallet, @Nullable BigDecimal fee, @Nullable BigInteger id, @Nullable BigInteger blockId)
            throws WebWalletCallException, ApiCallException {
        super(wallet, fee, id, blockId);
    }

    public UnregisterNode(Wallet wallet, @Nullable BigDecimal fee) throws WebWalletCallException, ApiCallException {
        this(wallet, fee, null, null);
    }

    public UnregisterNode(Wallet wallet) throws WebWalletCallException, ApiCallException {
        this(wallet, null);
    }

    @Override
    public String getName() {
        return "UnregisterNodeTransaction";
    }

    @Override
    protected void doBuild(Dict data, SHA3.DigestSHA3 digest) {

        data.put("public-key", wallet.getPublicKey());
        data.put("fee", fee.toString());

        //digest

        digest.update(wallet.getPublicKeyBytes());
    }
}
