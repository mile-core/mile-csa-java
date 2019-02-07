package global.mile.transactions;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class Digest {
    public static byte[] prepareId(BigInteger id) {
        ByteBuffer idBytes = ByteBuffer.allocate(Long.BYTES);
        idBytes.putLong(id.longValue());
        return reverse(idBytes.array());
    }

    public static byte[] prepareBlockId(BigInteger blockId) {
        byte[] blockIdBytes = new byte[32];
        byte[] blockIdShortBytes = blockId.toByteArray();
        System.arraycopy(blockIdShortBytes, 0, blockIdBytes, 32-blockIdShortBytes.length, blockIdShortBytes.length);
        return reverse(blockIdBytes);
    }

    public static byte[] prepareAssetCode(int assetCode) {
        ByteBuffer assetCodeBytes = ByteBuffer.allocate(Short.BYTES);
        assetCodeBytes.putShort((short) assetCode);
        return reverse(assetCodeBytes.array());
    }

    public static byte[] prepareFixedString(String str, int size) {
        byte[] bytes = new byte[size];
        System.arraycopy(str.getBytes(), 0, bytes, 0, Math.min(size, str.length()));
        return bytes;
    }

    protected static byte[] reverse(byte[] a) {
        byte[] res = new byte[a.length];
        System.arraycopy(a, 0, res, 0, a.length);
        for(int i = 0; i < res.length / 2; i++)
        {
            byte temp = res[i];
            res[i] = res[res.length - i - 1];
            res[res.length - i - 1] = temp;
        }

        return res;
    }
}
