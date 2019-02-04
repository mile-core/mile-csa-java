package global.mile.crypto;

import org.bitcoinj.core.Base58;

import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Signature extends Bytes {
    public static final int SIZE = 64;


    public Signature(byte[] data) {
        super(data);
        assert data.length == SIZE;
    }

}
