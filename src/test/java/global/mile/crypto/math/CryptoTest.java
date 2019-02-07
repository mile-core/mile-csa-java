package global.mile.crypto.math;

import global.mile.MileTestCase;
import global.mile.crypto.KeyPair;
import global.mile.crypto.Seed;
import global.mile.crypto.Signature;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.bitcoinj.core.Base58;

import java.security.SecureRandom;

import static global.mile.crypto.math.Crypto.*;

/**
 * Unit test for simple App.
 */
public class CryptoTest
    extends MileTestCase
{
    private String[][] keys;
    private String[][] signs;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CryptoTest(String testName )
    {
        super( testName );

        keys = new String[][]{
                {"secret-phrase", "XPBeWywB2XCgTVATLY5kYTTwNC5GwVqYyNcsTeLb7Mmn2KSNjTUzhxPJG88aiSWiuiXp7y7xHzCikNimmcz2a65mZgnBj", "eZrf8Eq1qDvSCEfdeezBRhT3QSf5GkrQH9tzGNaYt8q8gH5aZ"},
                {"", "YQhB8NDVGDEkNVWfyKvtJV9xhexcofbTm6T1w3jGDSCry9FrHFmGe3CYCJQmFQS9b1NSdp6VrUcTcWKHX1fnwQYigArQS", "g8oWAjqJKoqZb4isr23FLefhwqYgwPHV268Eh4uM3sFAmjzZh"},
                {"123", "WPpK9GeNJkdRsQYKYuikgyB95uPUrWDcZc7Z8v2b2rYzAs9kxGo87BmM4Wm82fZXN64XNJ5k7RSMXbN8Rs43MhRNpy9Pd", "k1hwqVoVyAkuWcrKGpnUHiLTEy7uNXZoRMB8ZUo472XYpNSZc"},
                {"Too loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong to be true", "N47wdvhED2c2hefyH4Gwsvh7pP6YzTjqt2axL7fk2pjcKbk79XgBocP2hEJm4DxvCF96GsgXpNF2hsjVEwsbc1Hjjzx5k", "EZYc9uuc8T4BWUjTndQoMhqRpTVAhWDbZ8DajgWkbB6q95sxm"},
                {"天翻地覆", "UEsVa5gQdf3sDSipnaNxnYAJL3s815KcYFzYkSrzq9eq8ZCq5r4JFPQZwLNumpobBUjJtQ9k9g716Ew61jzu4URnhpEX9", "YZ3v14VxwEzK6GimmXhJJv75bwYmQG3YkhhzFWpeYC16tQazq"},
                {"Лотус Майл", "FihWQVtCbx7RL97YT4hxYSeN9uqWXH7NNajT8wKKKt7K8zJjm6uJd4EmqMNu2srgzieV2YZXnsi8szQxPApWRsQxPwN13", "fjqr7r9dKL1TwpZycrzSBvsRcfpFR2mBRCpFWMaDPc3fkEtBx"},
        };

        signs = new String[][]{
                {"message", "2CRcWV9EtWQbCQy5876PNUkBYwxEujVkb81LoKgnBYwBU7P3EJ2vRXqgHWL3JJT6rCtJCQhKhpXkW83SB1sb3si1"},
                {"Lotus Mile", "2duKbN8SHfkC6CNaGG38aPHLVuz8nMHzAhG84h3M12mC6YY3A9pPSqwbbaiHvSxUykRYQHtPUekTiezoQ2Gm9uRe"},
                {"天翻地覆", "4atBJfgT5KMsgZYviurk29p18phFRbcWpU9XNc7BwCKXTjd18Sj5tuxWmy5pogM3W9SsT99hTxjt9jj562FR95vy"},
                {"Лотус Майл", "4poPC2eFUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbicBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
        };

    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CryptoTest.class );
    }


    public void testGenerateBySeed()
    {
        for (String[] case_ : keys) {
            KeyPair p = Crypto.createKeyPair(new Seed(case_[0]));
            assertEquals(case_[1], p.getPrivateKey().toBase58WithChecksum());
            assertEquals(case_[2], p.getPublicKey().toBase58WithChecksum());

            p = createKeyPair(case_[0]);
            assertEquals(case_[1], p.getPrivateKey().toBase58WithChecksum());
            assertEquals(case_[2], p.getPublicKey().toBase58WithChecksum());
        }

    }

    public void testRestoreFromPrivate()
    {
        KeyPair p = createKeyPair();
        KeyPair p2 = restoreKeyPairFromPrivateKey(p.getPrivateKey());

        assertEquals(p, p2);

        for (String[] case_ : keys) {
            p = createKeyPair(new Seed(case_[0]));
            p2 = restoreKeyPairFromPrivateKey(p.getPrivateKey());
            assertEquals(p, p2);
        }

    }

    public void testSign()
    {
        KeyPair p = createKeyPair("secret-phrase");

        for (String[] case_: signs) {
            Signature s = sign(case_[0].getBytes(), p.getPublicKey(), p.getPrivateKey());
            assertEquals(case_[1], toBase58(s));
        }

    }

    public void testSignVerify()
    {
        KeyPair p = createKeyPair("secret-phrase");


        for (String[] case_: signs) {
            assertTrue(verifySignature(case_[0].getBytes(), SignatureFromBase58(case_[1]), p.getPublicKey()));
        }

        String[][]incorrectSigns = {
                {"message1", "2CRcWV9EtWQbCQy5876PNUkBYwxEujVkb81LoKgnBYwBU7P3EJ2vRXqgHWL3JJT6rCtJCQhKhpXkW83SB1sb3si1"},
                {"1message", "2CRcWV9EtWQbCQy5876PNUkBYwxEujVkb81LoKgnBYwBU7P3EJ2vRXqgHWL3JJT6rCtJCQhKhpXkW83SB1sb3si1"},
                {"message", "2CRcW19EtWQbCQy5876PNUkBYwxEujVkb81LoKgnBYwBU7P3EJ2vRXqgHWL3JJT6rCtJCQhKhpXkW83SB1sb3si1"},
                {"message", "2CRcWV9EtWQbCQy5816PNUkBYwxEujVkb81LoKgnBYwBU7P3EJ2vRXqgHWL3JJT6rCtJCQhKhpXkW83SB1sb3si1"},
                {"message", "2CRcWV9EtWQbCQy5876PNUkBYwxEujVkb81LoKg1BYwBU7P3EJ2vRXqgHWL3JJT6rCtJCQhKhpXkW83SB1sb3si1"},
                {"message", "2CRcWV9EtWQbCQy5876PNUkBYwxEujVkb81LoKgnBYwBU7P3EJ2vRXqgHWL3JJ16rCtJCQhKhpXkW83SB1sb3si1"},
                {"message", "2CRcWV9EtWQbCQy5876PNUkBYwxEujVkb81LoKgnBYwBU7P3EJ2vRXqgHWL3JJT6rCtJCQhKhpXkW83SB1sb3s11"},

                {"Lotus  Mile", "2duKbN8SHfkC6CNaGG38aPHLVuz8nMHzAhG84h3M12mC6YY3A9pPSqwbbaiHvSxUykRYQHtPUekTiezoQ2Gm9uRe"},
                {"Lotus Mile ", "2duKbN8SHfkC6CNaGG38aPHLVuz8nMHzAhG84h3M12mC6YY3A9pPSqwbbaiHvSxUykRYQHtPUekTiezoQ2Gm9uRe"},
                {" Lotus Mile", "2duKbN8SHfkC6CNaGG38aPHLVuz8nMHzAhG84h3M12mC6YY3A9pPSqwbbaiHvSxUykRYQHtPUekTiezoQ2Gm9uRe"},
                {"Lotus1Mile", "2duKbN8SHfkC6CNaGG38aPHLVuz8nMHzAhG84h3M12mC6YY3A9pPSqwbbaiHvSxUykRYQHtPUekTiezoQ2Gm9uRe"},
                {"Lotus Mile", "2duKb18SHfkC6CNaGG38aPHLVuz8nMHzAhG84h3M12mC6YY3A9pPSqwbbaiHvSxUykRYQHtPUekTiezoQ2Gm9uRe"},
                {"Lotus Mile", "2duKbN8SHfkC6CNaGG38aPHLVuz8nMHzAhG84h3M12m16YY3A9pPSqwbbaiHvSxUykRYQHtPUekTiezoQ2Gm9uRe"},
                {"Lotus Mile", "2duKbN8SHfkC6CNaGG38aPHLVuz8nMHzAhG84h3M12mC6YY3A9pPSqwbbaiHvSxUykRYQHtP1ekTiezoQ2Gm9uRe"},

                {"天翻翻地覆", "4atBJfgT5KMsgZYviurk29p18phFRbcWpU9XNc7BwCKXTjd18Sj5tuxWmy5pogM3W9SsT99hTxjt9jj562FR95vy"},
                {"天翻地覆天", "4atBJfgT5KMsgZYviurk29p18phFRbcWpU9XNc7BwCKXTjd18Sj5tuxWmy5pogM3W9SsT99hTxjt9jj562FR95vy"},
                {"天翻天地覆", "4atBJfgT5KMsgZYviurk29p18phFRbcWpU9XNc7BwCKXTjd18Sj5tuxWmy5pogM3W9SsT99hTxjt9jj562FR95vy"},
                {"天翻 地覆", "4atBJfgT5KMsgZYviurk29p18phFRbcWpU9XNc7BwCKXTjd18Sj5tuxWmy5pogM3W9SsT99hTxjt9jj562FR95vy"},
                {" 天翻地覆", "4atBJfgT5KMsgZYviurk29p18phFRbcWpU9XNc7BwCKXTjd18Sj5tuxWmy5pogM3W9SsT99hTxjt9jj562FR95vy"},
                {"天翻地覆", "4atB1fgT5KMsgZYviurk29p18phFRbcWpU9XNc7BwCKXTjd18Sj5tuxWmy5pogM3W9SsT99hTxjt9jj562FR95vy"},
                {"天翻地覆", "4atBJfgT5KMsgZYviurk29p18phFRbcdpU9XNc7BwCKXTjd18Sj5tuxWmy5pogM3W9SsT99hTxjt9jj562FR95vy"},
                {"天翻地覆", "4atBJfgT5KMsgZYviurk29p18phFRbcWpU9XNc7BwCKXTjd18Sj5tuxWdy5pogM3W9SsT99hTxjt9jj562FR95vy"},
                {"天翻地覆", "4atBJfgT5KMsgZYviurk29p18phFRbcWpU9XNc7BwCKXTjd18Sj5tuxWmy5pogM3W9SsT99hTxjt9jj5d2FR95vy"},

                {"Лотус  Майл", "4poPC2eFUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbicBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
                {"Лотус Майл ", "4poPC2eFUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbicBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
                {" Лотус Майл", "4poPC2eFUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbicBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
                {"Лотус Майлл", "4poPC2eFUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbicBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
                {"Лотус МАйл", "4poPC2eFUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbicBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
                {"Лотус Майл", "4poPC21FUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbicBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
                {"Лотус Майл", "4poPC2eFUoG2HKimhB7ocgNfE3rT7LT1kHDzq7KbicBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
                {"Лотус Майл", "4poPC2eFUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbifBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
                {"Лотус Майл", "4poPC2eFUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbicBubB1nHhfj2nPZ4BEAEipFTfss2cMj7NuxvbyVQaKuPx88"},
                {"Лотус Майл", "4poPC2eFUoG2HKimhB7ocgN3E3rT7LT1kHDzq7KbicBubB1nHhPj2nPZ4BEAEipFTfss2cMj7NuxvbyfQaKuPx88"},
        };

        for (String[] case_: incorrectSigns) {
            assertFalse(verifySignature(case_[0].getBytes(), SignatureFromBase58(case_[1]), p.getPublicKey()));
        }
    }

    public void testMassComplex() {
        SecureRandom r = new SecureRandom();
        byte[] msg = new byte[256];
        byte[] wrongMsg;
        byte[] wrongSignature;
        KeyPair p;
        for(int i=0; i<1500; i++) {
            p = createKeyPair();
            r.nextBytes(msg);
            Signature s = sign(msg, p.getPublicKey(), p.getPrivateKey());
            assertTrue(verifySignature(msg, s, p.getPublicKey()));

            wrongMsg = msg.clone();
            wrongMsg[5] += 1;
            assertFalse(verifySignature(wrongMsg, s, p.getPublicKey()));

            wrongSignature = s.getData();
            wrongSignature[7] += 1;
            assertFalse(verifySignature(msg, new Signature(wrongSignature), p.getPublicKey()));
        }
    }


    static Signature SignatureFromBase58(String s) {
        return new Signature(Base58.decode(s));
    }

    static String toBase58(Signature s) {
        return Base58.encode(s.getData());
    }

}
