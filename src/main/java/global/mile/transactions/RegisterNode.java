package global.mile.transactions;

import global.mile.Dict;
import global.mile.Wallet;
import global.mile.errors.ApiCallException;
import global.mile.wallet.Asset;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;

public class RegisterNode extends TransactionWithFee {

    private final String address;
    private final BigDecimal amount;

    public RegisterNode(Wallet wallet, String address, BigDecimal amount, @Nullable BigDecimal fee)
            throws ApiCallException {
        super(wallet, fee);

        if (address.length() == 0) {
            throw new ApiCallException("Address length must not be empty");
        }
        if (address.length() > 64) {
            throw new ApiCallException("Address length must be <= 64");
        }
        this.address = address;
        this.amount = amount;
    }

    public RegisterNode(Wallet wallet, String address, BigDecimal amount) throws ApiCallException {
        this(wallet, address, amount, null);
    }

    @Override
    public String getName() {
        return "RegisterNodeTransactionWithAmount";
    }

    @Override
    protected void doBuild(Dict data, SHA3.DigestSHA3 digest) {

        String amountStr = Asset.normalizePrecision(amount, Asset.XDR).toString();

        data.put("public-key", wallet.getPublicKey());
        data.put("asset", Map.of("code", Asset.XDR, "amount", amountStr));
        data.put("address", address);
        data.put("fee", fee.toString());

        //digest

        digest.update(wallet.getPublicKeyBytes());
        digest.update(Digest.prepareFixedString(address, 64));
        digest.update(amountStr.getBytes());

    }
}
