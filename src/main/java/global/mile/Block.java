package global.mile;

import global.mile.transactions.Transaction;

import java.math.BigInteger;
import java.util.List;

public class Block {
    private BigInteger id;
    private String timestamp;
    private Dict data;
    private List<Transaction> transactions;

    public Block(BigInteger id, Dict data, String timestamp, List<Transaction> transactions) {
        this.id = id;
        this.timestamp = timestamp;
        this.data = data;
        this.transactions = transactions;
    }

    public BigInteger getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Dict getData() {
        return data;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
