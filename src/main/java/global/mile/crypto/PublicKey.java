package global.mile.crypto;

public class PublicKey extends Bytes {
    public static final int SIZE = 32;

    public PublicKey(byte[] data) {
        super(data);
        assert data.length == SIZE;
    }

    public static PublicKey fromBase58WithCheckSum(String str) {
        PublicKey publicKey = new PublicKey(dataFromBase58WithChecksum(str));
        assert publicKey.getData().length == SIZE;
        return publicKey;
    }
}
