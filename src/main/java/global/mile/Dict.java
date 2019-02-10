package global.mile;

import java.util.HashMap;
import java.util.Map;

public class Dict extends HashMap<String, Object> {

    public static Dict from(String k1, Object v1) {
        Dict dict = new Dict();
        dict.put(k1, v1);
        return dict;
    }

    public static Dict from(String k1, Object v1, String k2, Object v2) {
        Dict dict = Dict.from(k1, v1);
        dict.put(k2, v2);
        return dict;
    }

    public static Dict from(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        Dict dict = Dict.from(k1, v1, k2, v2);
        dict.put(k3, v3);
        return dict;
    }

    public static Dict from(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        Dict dict = Dict.from(k1, v1, k2, v2, k3, v3);
        dict.put(k4, v4);
        return dict;
    }

    public static Dict from(Map<Object, Object> map) {
        Dict dict = new Dict();
        for (Map.Entry<Object, Object> entry : map.entrySet())
        {
            assert entry.getKey() instanceof String;
            dict.put((String) entry.getKey(), entry.getValue());
        }
        return dict;
    }

    @Override
    public Object get(Object key) {
        Object res = super.get(key);
        if (res instanceof Map) {
            return Dict.from((Map<Object, Object>) res);
        }

        return res;
    }

}
