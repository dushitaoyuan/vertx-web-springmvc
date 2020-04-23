package com.taoyuanx.vertxdemo.nospring.web;

import com.taoyuanx.springmvc.vertx.core.core.interceptors.Interceptor;
import com.taoyuanx.springmvc.vertx.core.core.Order;
import com.taoyuanx.springmvc.vertx.core.core.interceptors.IRequestInterceptor;
import io.vertx.ext.web.RoutingContext;

/**
 * @author dushitaoyuan
 * @date 2020/4/21
 */
@Interceptor(value = "/api/*")
public class RouteInterceptorDemo implements IRequestInterceptor, Order {


    @Override
    public boolean pre(RoutingContext routingContext) {
        System.out.println("pre1");
        return true;
    }

    @Override
    public boolean after(RoutingContext routingContext) {
        /**
         * end 后 不执行
         */
        System.out.println("after1");
        return true;
    }

    @Override
    public int order() {
        return 100;
    }
}
