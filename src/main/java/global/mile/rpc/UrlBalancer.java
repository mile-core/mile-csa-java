package global.mile.rpc;

import java.util.List;

public class UrlBalancer {
    private final List<String> apiUrls;
    private final boolean useBalancing;
    private int currentUrlIndex;

    public UrlBalancer(List<String> apiUrls, boolean useBalancing) {

        this.apiUrls = apiUrls;
        this.useBalancing = useBalancing;
        this.currentUrlIndex = 0;
    }

    public String getUrl() {
        if (useBalancing) {
            return apiUrls.get(currentUrlIndex++ % apiUrls.size());
        } else {
            return apiUrls.get(0);
        }
    }
}
