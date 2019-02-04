package global.mile.crypto;

import org.bouncycastle.jcajce.provider.digest.SHA3;

import static java.lang.Math.min;

public class Seed extends Bytes {

    public Seed(String phrase) {
        super(new SHA3.Digest256().digest(phrase.getBytes()));
    }

    public Seed(byte[] data) {
        super(data);
    }

}
