package global.mile;

public class Config {

    public static String version = "1";
    public static String url = "https://wallet.mile.global";
    public static String appSchema = "mile-core:";
    public static boolean sslVerification = true;
    public static int connectionTimeout = 30;
    public static boolean useBalancing = true;
    public static boolean rpcDebug = false;

    public static class Web {
        public static String path = "shared";
        public static String url = "https://wallet.mile.global";

        public static String nodesUrls() {
            return url + "/v" + Config.version + "/nodes.json";
        }
        
        public static String baseUrl() {
            return url + "/v" + Config.version;
        }
        
        public static class Wallet {
            public static String publicKey = "/" + Web.path + "/wallet/key/public/";
            public static String privateKey = "/" + Web.path + "/wallet/key/private/";
            public static String note = "/" + Web.path + "/wallet/note/";
            public static String name = "/" + Web.path + "/wallet/note/name/";
            public static String secretPhrase = "/" + Web.path + "/wallet/secret/phrase/";
            public static String amount = "/" + Web.path + "/wallet/amount/";
        };

        public static class Payment {
            public static String publicKey = "/" + Web.path + "/payment/key/public/";
            public static String amount = "/" + Web.path + "/payment/amount/";
        }
    }
    

}
