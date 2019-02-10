package global.mile.wallet;

import global.mile.Wallet;
import global.mile.errors.MileException;

public class PublicKeyWallet extends Wallet
{
    private String publicKey;

    public PublicKeyWallet() throws MileException {
        throw new MileException("Not allowed");
    }

    public PublicKeyWallet(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String getPrivateKey() {
        return null;
    }
}
