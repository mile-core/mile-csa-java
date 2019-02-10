package global.mile.transactions;

import global.mile.Dict;
import global.mile.Wallet;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class UnregisterNode extends TransactionWithFee {


    public UnregisterNode(Wallet wallet, @Nullable BigDecimal fee) {
        super(wallet, fee);
    }

    public UnregisterNode(Wallet wallet) {
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

    @Override
    public String toString() {
        return getName()
                + ": wallet: " + wallet.getPublicKey()
                + ", fee: " + fee.toString();
    }
}
