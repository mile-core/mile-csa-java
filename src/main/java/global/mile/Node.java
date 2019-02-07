package global.mile;

import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;
import global.mile.transactions.PostTokenRate;
import global.mile.transactions.RegisterNode;
import global.mile.transactions.UnregisterNode;
import global.mile.wallet.Wallet;

import java.math.BigDecimal;

public class Node {
    private final Wallet wallet;
    private final String address;

    public Node(Wallet wallet, String address) {
        this.wallet = wallet;
        this.address = address;
    }

    public boolean register(BigDecimal amount, BigDecimal fee) throws WebWalletCallException, ApiCallException {
        RegisterNode tx = new RegisterNode(this.wallet, address, amount, fee);
        return tx.send();
    }

    public boolean register(BigDecimal amount) throws WebWalletCallException, ApiCallException {
        return register(amount, new BigDecimal("0"));
    }

    public boolean unregister(BigDecimal fee) throws WebWalletCallException, ApiCallException {
        UnregisterNode tx = new UnregisterNode(this.wallet, fee);
        return tx.send();
    }

    public boolean unregister() throws WebWalletCallException, ApiCallException {
        return unregister(new BigDecimal("0"));
    }

    public boolean postTokenRate(BigDecimal rate, BigDecimal fee) throws WebWalletCallException, ApiCallException {
        PostTokenRate tx = new PostTokenRate(this.wallet, rate, fee);
        return tx.send();
    }

    public boolean postTokenRate(BigDecimal rate) throws WebWalletCallException, ApiCallException {
        return postTokenRate(rate, new BigDecimal("0"));
    }
}
