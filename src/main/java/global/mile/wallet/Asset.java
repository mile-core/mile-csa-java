package global.mile.wallet;

public class Asset {
    private String name;
    private int code;

    public Asset(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
