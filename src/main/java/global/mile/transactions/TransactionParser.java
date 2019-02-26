package global.mile.transactions;

import global.mile.Dict;
import global.mile.Wallet;
import global.mile.wallet.PublicKeyWallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransactionParser {
    public static List<Transaction> parse(Object transactions) {
        List<Transaction> res = new ArrayList<>();

        if (!(transactions instanceof List)) {
            return res;
        }

        for(Object transaction : (List) transactions) {
            if (!(transaction instanceof Map)) {
                continue;
            }

            String type = ((Map) transaction).get("transaction-type").toString();

            switch (type) {
                case "TransferAssetsTransaction": {
                    for (Object asset :  (List)((Map) transaction).get("asset")) {
                        res.add(
                                new Transfer(
                                        new PublicKeyWallet(((Map) transaction).get("from").toString()),
                                        Integer.parseInt(((Map) asset).get("code").toString()),
                                        new BigDecimal(((Map) asset).get("amount").toString()),
                                        ((Map) transaction).get("to").toString(),
                                        ((Map) transaction).get("description").toString(),
                                        new BigDecimal(((Map) transaction).get("fee").toString())
                                )
                        );
                    }
                    break;
                }
                default:
                    break;
            }
        }

        return res;
    }
}
