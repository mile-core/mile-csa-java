package global.mile.transactions;

import global.mile.Chain;
import global.mile.Dict;
import global.mile.Wallet;
import global.mile.crypto.Bytes;
import global.mile.crypto.Signature;
import global.mile.errors.ApiCallException;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nonnull;
import java.math.BigInteger;

abstract public class Transaction {

    protected Wallet wallet;

    public Transaction(Wallet wallet) {
        this.wallet = wallet;
    }

    public boolean send(Chain chain) throws ApiCallException {
        return chain.sendTransaction(this);
    }

    public Wallet getWallet() {
        return wallet;
    }

    abstract public String getName();

    public final Dict build(@Nonnull BigInteger id, @Nonnull BigInteger blockId) {
        Dict data = new Dict();
        data.put("transaction-type", getName());

        data.put("transaction-id", id.toString());
        data.put("block-id", blockId.toString());

        SHA3.DigestSHA3 digest = new SHA3.Digest256();
        digest.update(Digest.prepareId(id));
        digest.update(Digest.prepareBlockId(blockId));

        /////////////////

        doBuild(data, digest);

        /////////////////

        Bytes digestBytes = new Bytes(digest.digest());
        Signature s = wallet.sign(digestBytes.getData());

        data.put("digest", digestBytes.toBase58WithChecksum());
        data.put("signature", s.toBase58WithChecksum());

        return data;
    }

    abstract protected void doBuild(Dict data, SHA3.DigestSHA3 digest);

    abstract public String toString();
}
