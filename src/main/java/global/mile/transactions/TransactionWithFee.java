package global.mile.transactions;

import global.mile.Dict;
import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;
import global.mile.wallet.Wallet;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;

abstract public class TransactionWithFee extends Transaction {

    protected BigDecimal fee;

    public TransactionWithFee(Wallet wallet,  @Nullable BigDecimal fee, @Nullable BigInteger id, @Nullable BigInteger blockId)
            throws WebWalletCallException, ApiCallException {
        super(wallet, id, blockId);
        if (fee == null) {
            fee = new BigDecimal("0");
        }
        this.fee = fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

}
