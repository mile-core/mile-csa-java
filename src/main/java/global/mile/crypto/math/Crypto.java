package global.mile.crypto.math;

import global.mile.crypto.*;
import org.bouncycastle.crypto.digests.SHA512Digest;

import java.security.SecureRandom;


public abstract class Crypto
{
    public static KeyPair createKeyPair(Seed seed) {
        SHA512Digest d = new SHA512Digest();
        byte[] privateKeyData = new byte[d.getDigestSize()];

        d.update(seed.getData(), 0, seed.getData().length);
        d.doFinal(privateKeyData, 0);

        privateKeyData[0] &= 248;
        privateKeyData[31] &= 63;
        privateKeyData[31] |= 64;

        byte[] publicKeyData = new byte[PublicKey.SIZE];

        Ed25519.scalarMultBaseEncoded(privateKeyData, publicKeyData, 0);
        return new KeyPair(new PrivateKey(privateKeyData), new PublicKey(publicKeyData));
    }

    public static KeyPair createKeyPair(String seed) {
        return createKeyPair(new Seed(seed));
    }

    public static KeyPair createKeyPair() {
        SecureRandom r = new SecureRandom();
        byte[] bytes = new byte[32];
        r.nextBytes(bytes);
        return createKeyPair(new Seed(bytes));
    }

    public static KeyPair restoreKeyPairFromPrivateKey(PrivateKey privateKey) {
        byte[] publicKeyData = new byte[PublicKey.SIZE];

        Ed25519.scalarMultBaseEncoded(privateKey.getData(), publicKeyData, 0);
        return new KeyPair(privateKey, new PublicKey(publicKeyData));
    }

    public static Signature sign(byte[] message, PublicKey publicKey, PrivateKey privateKey) {
        // used implementation from bouncycastle

        SHA512Digest d = new SHA512Digest();
        byte[] privKeyEntropy = new byte[64];
        System.arraycopy(privateKey.getData(), 32, privKeyEntropy, 32, 32);

        byte[] signatureData = new byte[Signature.SIZE];

        Ed25519.implSign(
                d, privKeyEntropy, privateKey.getData(), publicKey.getData(),
                0, message, 0, message.length, signatureData, 0
        );

        return new Signature(signatureData);
    }


    public static boolean verifySignature(byte[] message, Signature signature, PublicKey publicKey) {
        return Ed25519.verify(
                signature.getData(), 0,
                publicKey.getData(), 0,
                message, 0, message.length
        );
    }



}