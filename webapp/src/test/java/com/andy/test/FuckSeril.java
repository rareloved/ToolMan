package com.andy.test;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>DESCRIPTION:  类描述
 * <p>CALLED BY:   zhangshouzheng
 * <p>UPDATE BY:   zhangshouzheng
 * <p>CREATE DATE: 2016/12/1
 * <p>UPDATE DATE: 2016/12/1
 *
 * @version 1.0
 * @since java 1.7.0
 */
public class FuckSeril {
    /**
     * 反序列化。将String反序列化为Map。
     */
    public static Map<String, Object> unserialize(String feature) {
        if (feature == null || "".equals(feature.trim())) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        String[] kvs = feature.split("[|]");
        for (String str : kvs) {
            String[] kv = str.split(":");
            if (kv != null && kv.length > 1) {
                String key = kv[0];
                String value = kv[1];
                map.put(key, value);
            }
        }

        return map;
    }

    public static void main(String[] args) {
        System.out.println(FuckSeril.unserialize("promotion:JXQ20160825-14-32-207712|promotype:2"));
    }
}
