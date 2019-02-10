package global.mile.transactions;

import global.mile.Wallet;

import javax.annotation.Nullable;
import java.math.BigDecimal;

abstract public class TransactionWithFee extends Transaction {

    protected BigDecimal fee;

    public TransactionWithFee(Wallet wallet, @Nullable BigDecimal fee) {
        super(wallet);
        if (fee == null) {
            fee = new BigDecimal("0");
        }
        this.fee = fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

}
