package com.taoyuanx.springmvc.vertx.core.util;

import java.util.Objects;

/**
 * @author dushitaoyuan
 * @date 2020/4/23
 */
public class TemplateUtil {
    public static String templateSuffix(String templateFileName) {
        int index = templateFileName.lastIndexOf(".");
        if (index > 0) {
            return templateFileName.substring(index + 1).toLowerCase();
        }
        return null;
    }

    public static String templatePath(String basePath, String templateFileName) {
        if (StringUtil.isEmpty(basePath)) {
            return templateFileName;
        }
        if (templateFileName.startsWith(basePath)) {
            return templateFileName;
        }
        return basePath + templateFileName;
    }

}
