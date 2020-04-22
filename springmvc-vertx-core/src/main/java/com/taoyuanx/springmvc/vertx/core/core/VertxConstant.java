package com.taoyuanx.springmvc.vertx.core.core;

/**
 * @author dushitaoyuan
 * @date 2020/4/21
 */
public class VertxConstant {
    /**
     * 拦截器默认route order
     */
    public static final int INTERCEPTOR_PRE_DEFAULT_ORDER = Integer.MIN_VALUE;

    /**
     * 自动注册路由order
     */
    public static final int AUTO_REGIST_ROUTE_ORDER = Integer.MIN_VALUE + 100000;


    public static final int DEFAULT_SERVER_PORT = 8080;
    public static final int DEFAULT_EVENTBUS_CONNECTTIMEOUT = 2000;


    public static final String DEALUT_STATIC_DIR = "public,static";
}
