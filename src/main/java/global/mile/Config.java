package global.mile;

import global.mile.errors.WebWalletCallException;

import java.util.Arrays;
import java.util.List;

public class Config {

    private String apiVersion;
    private boolean useBalancing;
    private List<String> apiUrls;
    private int connectionTimeout;

    private String webWalletUrl;


    public Config(String apiVersion, List<String> apiUrls,
                  boolean useBalancing, int connectionTimeout, String webWalletUrl
    ) {
        this.apiVersion = apiVersion;
        this.apiUrls = apiUrls;
        this.useBalancing = useBalancing;
        this.connectionTimeout = connectionTimeout;
        this.webWalletUrl = webWalletUrl;
    }

    public static Config.Builder custom() {
        return new Builder();
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public boolean isUseBalancing() {
        return useBalancing;
    }

    public List<String> getApiUrls() {
        return apiUrls;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public String getWebWalletUrl() {
        return webWalletUrl;
    }

    public static class Builder {
        private String apiVersion = "1";
        private boolean useBalancing = true;
        private List<String> apiUrls = Arrays.asList("https://lotus000.i.mile.global");
        private int connectionTimeout = 30;
        private String webWalletUrl = "https://wallet.mile.global";


        public Config build() {
            return new Config(apiVersion, apiUrls, useBalancing, connectionTimeout, webWalletUrl);
        }

        public Builder setApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        public Builder setUseBalancing(boolean useBalancing) {
            this.useBalancing = useBalancing;
            return this;
        }

        public Builder setApiUrls(List<String> apiUrls) {
            this.apiUrls = apiUrls;
            return this;
        }

        public Builder fetchApiUrlsFromWebWallet() throws WebWalletCallException {
            this.apiUrls = WebWallet.fetchApiUrls(this.webWalletUrl);
            return this;
        }

        public Builder setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder setWebWalletUrl(String webWalletUrl) {
            this.webWalletUrl = webWalletUrl;
            return this;
        }
    }

}
