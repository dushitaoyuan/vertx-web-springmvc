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

    /**
     * 默认静态资源目录
     */
    public static final String DEALUT_STATIC_DIR = "public,static";

    public static final String DEALUT_TEMPLATE_BASE_PATH = "templates/";
    /**
     * 默认模板引擎名称
     */
    public static final String DEFAULT_TEMPLATE_ENGINE_NAME = "default";

    /**
     * data_model key
     */
    public static final String TEMPLATE_DATA_MODEL = "_vd";

}
