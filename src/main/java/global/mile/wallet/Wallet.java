package global.mile.wallet;

import global.mile.Chain;
import global.mile.Dict;
import global.mile.crypto.KeyPair;
import global.mile.crypto.PrivateKey;
import global.mile.crypto.PublicKey;
import global.mile.crypto.Signature;
import global.mile.crypto.math.Crypto;
import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;
import global.mile.rpc.GetInfo;
import global.mile.transactions.Transfer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static global.mile.crypto.math.Crypto.createKeyPair;
import static global.mile.crypto.math.Crypto.restoreKeyPairFromPrivateKey;

public class Wallet {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    private Chain chain;

    public Wallet(KeyPair pair) {
        publicKey = pair.getPublicKey();
        privateKey = pair.getPrivateKey();
    }

    public Wallet() {
        this(createKeyPair());
    }

    public Wallet(String phrase) {
        this(createKeyPair(phrase));
    }

    public static Wallet fromPrivateKey(String privateKey) {
        return new Wallet(restoreKeyPairFromPrivateKey(PrivateKey.fromBase58WithCheckSum(privateKey)));
    }

    public String getPublicKey() {
        return publicKey.toBase58WithChecksum();
    }

    public String getPrivateKey() {
        return privateKey.toBase58WithChecksum();
    }

    public byte[] getPublicKeyBytes() {
        return publicKey.getData();
    }

    public byte[] getPrivateKeyBytes() {
        return privateKey.getData();
    }

    public Chain getChain() throws WebWalletCallException, ApiCallException {
        if (chain == null) {
            chain = Chain.getInstance();
        }

        return chain;
    }

    public Signature sign(byte[] message) {
        return Crypto.sign(message, publicKey, privateKey);
    }

    public State getState() throws WebWalletCallException, ApiCallException {
        Dict params = new Dict();
        params.put("public-key", this.getPublicKey());
        GetInfo i = new GetInfo("get-wallet-state", params);
        Dict data = i.exec();

        if (data.get("preferred-transaction-id") == null) {
            throw new ApiCallException("");
        }

        List<Balance> balances = new ArrayList<Balance>();
        if (data.get("balance") instanceof List) {
            for (Object item : (List) data.get("balance")) {
                if (! (item instanceof Map) ) {
                    throw new ApiCallException("");
                }

                int assetCode = Integer.parseInt(((Map) item).get("code").toString());

                balances.add(
                        new Balance(
                                new Asset(getChain().getAssetName(assetCode), assetCode),
                                new BigDecimal(((Map) item).get("amount").toString())
                        )
                );

            }
        }


        return new State(new BigInteger(data.get("preferred-transaction-id").toString()), balances);
    }

    public boolean transfer(String destination, int assetCode, BigDecimal amount, String description, BigDecimal fee)
            throws WebWalletCallException, ApiCallException {
        Transfer tx = new Transfer(this, assetCode, amount, destination, description, fee);
        return tx.send();
    }

    public boolean transfer(String destination, int assetCode, BigDecimal amount)
            throws WebWalletCallException, ApiCallException {
        return transfer(destination, assetCode, amount, "", new BigDecimal("0"));
    }

    public boolean transfer(Wallet destination, int assetCode, BigDecimal amount, String description, BigDecimal fee)
            throws WebWalletCallException, ApiCallException {
        return transfer(destination.getPublicKey(), assetCode, amount, description, fee);
    }

    public boolean transfer(Wallet destination, int assetCode, BigDecimal amount)
            throws WebWalletCallException, ApiCallException {
        return transfer(destination.getPublicKey(), assetCode, amount);
    }

    ////////////////////////////////

    public static class State {
        private BigInteger preferredTransactionId;
        private List<Balance> balances;


        public State(BigInteger preferredTransactionId, List<Balance> balances) {
            this.preferredTransactionId = preferredTransactionId;
            this.balances = balances;
        }

        public BigInteger getPreferredTransactionId() {
            return preferredTransactionId;
        }

        public List<Balance> getBalances() {
            return balances;
        }
    }
}
