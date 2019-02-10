package global.mile;

import global.mile.errors.ApiCallException;
import global.mile.errors.MileException;
import global.mile.transactions.PostTokenRate;
import global.mile.transactions.RegisterNode;
import global.mile.transactions.UnregisterNode;

import java.math.BigDecimal;

public class Node {
    private final Wallet wallet;
    private final String address;

    public Node(Wallet wallet, String address) {
        this.wallet = wallet;
        this.address = address;
    }

    public boolean register(Chain chain, BigDecimal amount, BigDecimal fee) throws MileException {
        RegisterNode tx = new RegisterNode(this.wallet, address, amount, fee);
        return tx.send(chain);
    }

    public boolean register(Chain chain, BigDecimal amount) throws MileException {
        return register(chain, amount, new BigDecimal("0"));
    }

    public boolean unregister(Chain chain, BigDecimal fee) throws ApiCallException {
        UnregisterNode tx = new UnregisterNode(this.wallet, fee);
        return tx.send(chain);
    }

    public boolean unregister(Chain chain) throws ApiCallException {
        return unregister(chain, new BigDecimal("0"));
    }

    public boolean postTokenRate(Chain chain, BigDecimal rate, BigDecimal fee) throws ApiCallException {
        PostTokenRate tx = new PostTokenRate(this.wallet, rate, fee);
        return tx.send(chain);
    }

    public boolean postTokenRate(Chain chain, BigDecimal rate) throws ApiCallException {
        return postTokenRate(chain, rate, new BigDecimal("0"));
    }
}
