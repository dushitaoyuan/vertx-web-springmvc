package com.taoyuanx.springmvc.vertx.core.util;

/**
 * @author dushitaoyuan
 * @date 2020/4/16
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}
