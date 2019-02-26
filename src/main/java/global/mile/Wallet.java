package global.mile;

import global.mile.crypto.KeyPair;
import global.mile.crypto.PrivateKey;
import global.mile.crypto.Signature;
import global.mile.errors.ApiCallException;
import global.mile.transactions.Transfer;
import global.mile.wallet.Asset;
import global.mile.wallet.Balance;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static global.mile.crypto.math.Crypto.createKeyPair;
import static global.mile.crypto.math.Crypto.restoreKeyPairFromPrivateKey;

public class Wallet {
    private KeyPair keyPair;

    private Chain chain;

    public Wallet(KeyPair pair) {
        keyPair = pair;
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
        return keyPair.getPublicKey().toBase58WithChecksum();
    }

    public String getPrivateKey() {
        return keyPair.getPrivateKey().toBase58WithChecksum();
    }

    public byte[] getPublicKeyBytes() {
        return keyPair.getPublicKey().getData();
    }

    public Signature sign(byte[] message) {
        return keyPair.sign(message);
    }

    public State getState(Chain chain) throws  ApiCallException {
        Dict params = new Dict();
        params.put("public-key", this.getPublicKey());
        Dict data = chain.getInfo("get-wallet-state", params);

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
                                new Asset(chain.getAssetName(assetCode), assetCode),
                                new BigDecimal(((Map) item).get("amount").toString())
                        )
                );

            }
        }


        return new State(new BigInteger(data.get("preferred-transaction-id").toString()), balances);
    }

    ////////////////////////////////

    public boolean transfer(Chain chain, String destination, int assetCode, BigDecimal amount, String description, BigDecimal fee)
            throws ApiCallException {
        Transfer tx = new Transfer(this, assetCode, amount, destination, description, fee);
        return tx.send(chain);
    }

    public boolean transfer(Chain chain, String destination, int assetCode, BigDecimal amount)
            throws ApiCallException {
        return transfer(chain, destination, assetCode, amount, "", new BigDecimal("0"));
    }

    public boolean transfer(Chain chain, Wallet destination, int assetCode, BigDecimal amount, String description, BigDecimal fee)
            throws ApiCallException {
        return transfer(chain, destination.getPublicKey(), assetCode, amount, description, fee);
    }

    public boolean transfer(Chain chain, Wallet destination, int assetCode, BigDecimal amount)
            throws ApiCallException {
        return transfer(chain, destination.getPublicKey(), assetCode, amount);
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
