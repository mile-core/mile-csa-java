package global.mile.crypto;

public final class PrivateKey extends Bytes {
    public static final int SIZE = 64;

    public PrivateKey(byte[] data) {
        super(data);
        assert data.length == SIZE;
    }

    public static PrivateKey fromBase58WithCheckSum(String str) {
        PrivateKey privateKey = new PrivateKey(dataFromBase58WithChecksum(str));
        assert privateKey.getData().length == SIZE;
        return privateKey;
    }
}
