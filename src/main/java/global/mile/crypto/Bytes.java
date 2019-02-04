package global.mile.crypto;

import org.bitcoinj.core.Base58;

import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Bytes {
    protected byte[] data;

    public Bytes(byte[] data) {
        this.data = data.clone();
    }

    public String toBase58WithChecksum() {
        Checksum checksum = new CRC32();
        checksum.update(this.data, 0, data.length);
        long checksumValue = checksum.getValue();

        byte[] keyWithChecksum = new byte[data.length + 4];
        System.arraycopy(this.data, 0, keyWithChecksum, 0, data.length);
        keyWithChecksum[keyWithChecksum.length - 4] = (byte)(checksumValue & 0xff);
        keyWithChecksum[keyWithChecksum.length - 3] = (byte)((checksumValue >> 8) & 0xff);
        keyWithChecksum[keyWithChecksum.length - 2] = (byte)((checksumValue >> 16) & 0xff);
        keyWithChecksum[keyWithChecksum.length - 1] = (byte)((checksumValue >> 24) & 0xff);

        return Base58.encode(keyWithChecksum);
    }

    protected static byte[] dataFromBase58WithChecksum(String s) {
        byte[] dataWithChecksum = Base58.decode(s);
        assert dataWithChecksum.length > 4;

        byte[] data = new byte[dataWithChecksum.length-4];
        System.arraycopy(dataWithChecksum, 0, data, 0, dataWithChecksum.length-4);

        Checksum checksum = new CRC32();
        checksum.update(data, 0, data.length);
        long checksumValue = checksum.getValue();

        assert dataWithChecksum[dataWithChecksum.length - 4] == (byte)(checksumValue & 0xff);
        assert dataWithChecksum[dataWithChecksum.length - 3] == (byte)((checksumValue >> 8) & 0xff);
        assert dataWithChecksum[dataWithChecksum.length - 2] == (byte)((checksumValue >> 16) & 0xff);
        assert dataWithChecksum[dataWithChecksum.length - 1] == (byte)((checksumValue >> 24) & 0xff);

        return data;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Bytes))return false;
        Bytes otherMyClass = (Bytes)other;

        return Arrays.equals(this.data, otherMyClass.getData());
    }
}
