package com.taoyuanx.springmvc.vertx.core.interceptors;

import io.vertx.ext.web.RoutingContext;

/**
 * @author dushitaoyuan
 * @desc 请求拦截器
 * @date 2020/4/16
 */
public interface IRequestInterceptor {
    /**
     * return 是否继续执行
     */
    boolean pre(RoutingContext routingContext);

    /**
     * return 是否继续执行
     */
    boolean after(RoutingContext routingContext);


}
