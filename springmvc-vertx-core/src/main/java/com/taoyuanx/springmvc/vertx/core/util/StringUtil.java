package com.taoyuanx.springmvc.vertx.core.util;

import javax.swing.*;
import java.util.Collection;
import java.util.Objects;

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

    public static boolean isEmpty(Collection collection) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }
}
