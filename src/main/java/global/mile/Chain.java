package global.mile;

import global.mile.errors.ApiCallException;
import global.mile.errors.WebWalletCallException;
import global.mile.rpc.GetInfo;
import global.mile.transactions.Digest;

import java.math.BigInteger;
import java.util.*;

public class Chain {
    private String project;
    private String version;
    private List<String> supportedTransactions;
    private List<Map<String, String>> supportedAssets;
    private Map<Integer, String> assetsNameByCode;
    private Map<String, Integer> assetsCodeByName;

    private static Chain chain;

    public static Chain getInstance() throws WebWalletCallException, ApiCallException {
        if (chain == null) {
            chain = new Chain();
            Dict data = new GetInfo("get-blockchain-info").exec();
            chain.project = data.get("project").toString();
            chain.version = data.get("version").toString();

            chain.supportedTransactions = new ArrayList<>();
            Object supportedTransactions = data.get("supported-transaction-types");
            if (!(supportedTransactions instanceof List)) {
                throw new ApiCallException("Invalid supported-transaction-types");
            }
            for (Object item: (List) supportedTransactions) {
                chain.supportedTransactions.add(item.toString());
            }

            chain.supportedAssets = new ArrayList<Map<String, String>>();
            chain.assetsNameByCode = new HashMap<Integer, String>();
            chain.assetsCodeByName = new HashMap<String, Integer>();
            Object supportedAssets = data.get("supported-assets");
            if (!(supportedAssets instanceof List)) {
                throw new ApiCallException("Invalid supported-assets");
            }
            for (Map<String, String> item: (List<Map<String, String>>) supportedAssets) {
                chain.supportedAssets.add(item);
                chain.assetsNameByCode.put(Integer.parseInt(item.get("code").toString()), item.get("name").toString());
                chain.assetsCodeByName.put(item.get("name").toString(), Integer.parseInt(item.get("code").toString()));
            }
        }

        return chain;
    }

    public static BigInteger getCurrentBlockId() throws WebWalletCallException, ApiCallException {
        GetInfo i = new GetInfo("get-current-block-id", new Dict());
        Dict data = i.exec();
        if (data.get("current-block-id") == null) {
            throw new ApiCallException("Invalid chain response");
        }

        return new BigInteger(data.get("current-block-id").toString());
    }

    public String getAssetName(int assetCode) {
        return assetsNameByCode.get(assetCode);
    }

    public int getAssetCode(String assetName) {
        return assetsCodeByName.get(assetName);
    }
}
