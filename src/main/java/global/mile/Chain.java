package global.mile;

import global.mile.errors.ApiCallException;
import global.mile.rpc.GetInfo;
import global.mile.rpc.RpcFactory;
import global.mile.transactions.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chain {
    private String project;
    private String version;
    private List<String> supportedTransactions;
    private Map<Integer, String> assetsNameByCode;
    private Map<String, Integer> assetsCodeByName;
    private boolean updatedChainInfo = false;

    private Config config;
    private RpcFactory rpcFactory;

    public Chain(Config config) {

        this.config = config;
        this.rpcFactory = new RpcFactory(config);
    }

    public BigInteger getCurrentBlockId() throws ApiCallException {
        GetInfo i = rpcFactory.createGetInfo("get-current-block-id");
        Dict data = i.exec();
        if (data.get("current-block-id") == null) {
            throw new ApiCallException("Invalid chain response");
        }

        return new BigInteger(data.get("current-block-id").toString());
    }

    public String getProject() throws ApiCallException {
        if (!updatedChainInfo) {
            updateChainInfo();
            updatedChainInfo = true;
        }
        return project;
    }

    public String getVersion() throws ApiCallException {
        if (!updatedChainInfo) {
            updateChainInfo();
            updatedChainInfo = true;
        }
        return version;
    }

    public List<String> getSupportedTransactions() throws ApiCallException {
        if (!updatedChainInfo) {
            updateChainInfo();
            updatedChainInfo = true;
        }
        return supportedTransactions;
    }

    public String getAssetName(int assetCode) throws ApiCallException {
        if (!updatedChainInfo) {
            updateChainInfo();
            updatedChainInfo = true;
        }
        return assetsNameByCode.get(assetCode);
    }

    public int getAssetCode(String assetName) throws ApiCallException {
        if (!updatedChainInfo) {
            updateChainInfo();
            updatedChainInfo = true;
        }
        return assetsCodeByName.get(assetName);
    }


    public void updateChainInfo() throws ApiCallException {
        Dict data = rpcFactory.createGetInfo("get-blockchain-info").exec();
        project = data.get("project").toString();
        version = data.get("version").toString();

        supportedTransactions = new ArrayList<>();
        Object supportedTransactions = data.get("supported-transaction-types");
        if (!(supportedTransactions instanceof List)) {
            throw new ApiCallException("Invalid supported-transaction-types");
        }
        for (Object item: (List) supportedTransactions) {
            this.supportedTransactions.add(item.toString());
        }

        assetsNameByCode = new HashMap<Integer, String>();
        assetsCodeByName = new HashMap<String, Integer>();
        Object supportedAssets = data.get("supported-assets");
        if (!(supportedAssets instanceof List)) {
            throw new ApiCallException("Invalid supported-assets");
        }
        for (Map<String, String> item: (List<Map<String, String>>) supportedAssets) {
            assetsNameByCode.put(Integer.parseInt(item.get("code").toString()), item.get("name").toString());
            assetsCodeByName.put(item.get("name").toString(), Integer.parseInt(item.get("code").toString()));
        }
    }

    public Dict getInfo(String method, Dict params) throws ApiCallException {
        return rpcFactory.createGetInfo(method, params).exec();
    }

    public boolean sendTransaction(Transaction transaction) throws ApiCallException {
        BigInteger blockId = this.getCurrentBlockId();
        BigInteger transactionId = transaction.getWallet().getState(this).getPreferredTransactionId();
        return rpcFactory.createSendTransaction(transaction.build(transactionId, blockId)).exec();
    }

}
