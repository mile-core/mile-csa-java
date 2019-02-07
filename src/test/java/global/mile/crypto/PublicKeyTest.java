package global.mile.crypto;


import global.mile.MileTestCase;

import static global.mile.crypto.math.Crypto.createKeyPair;

public class PublicKeyTest extends MileTestCase {

    public void testFromBase58WithCheckSum() {
        KeyPair p = createKeyPair();

        PublicKey pub = PublicKey.fromBase58WithCheckSum(p.getPublicKey().toBase58WithChecksum());
        assertEquals(pub, p.getPublicKey());
    }
}