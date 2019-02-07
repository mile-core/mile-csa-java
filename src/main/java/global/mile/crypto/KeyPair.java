package global.mile.crypto;

import global.mile.crypto.math.Crypto;

public class KeyPair {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeyPair(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public Signature sign(byte[] message) {
        return Crypto.sign(message, publicKey, privateKey);
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof KeyPair))return false;
        KeyPair otherMyClass = (KeyPair)other;

        return this.privateKey.equals(otherMyClass.getPrivateKey())
                && this.publicKey.equals(otherMyClass.getPublicKey());
    }
}
