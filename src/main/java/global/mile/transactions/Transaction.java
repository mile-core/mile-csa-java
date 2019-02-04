package global.mile.transactions;

import global.mile.Chain;
import global.mile.Dict;
import global.mile.crypto.Bytes;
import global.mile.crypto.Signature;
import global.mile.crypto.math.Crypto;
import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;
import global.mile.rpc.SendTransaction;
import global.mile.wallet.Wallet;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.nio.ByteBuffer;

abstract public class Transaction {

    protected Wallet wallet;
    protected BigInteger id;
    protected BigInteger blockId;


    public Transaction(Wallet wallet, @Nullable BigInteger id, @Nullable BigInteger blockId)
            throws WebWalletCallException, ApiCallException {
        this.wallet = wallet;

        if (id == null) {
            this.id = wallet.getState().getPreferredTransactionId();
        } else {
            this.id = id;
        }

        if (blockId == null) {
            this.blockId = Chain.getCurrentBlockId();
        } else {
            this.blockId = blockId;
        }

    }

    public boolean send() throws WebWalletCallException, ApiCallException {
        Dict data = this.build();
        SendTransaction t = new SendTransaction(data);
        return t.exec();
    }

    abstract public String getName();

    public final Dict build() {
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
}
