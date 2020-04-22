package com.taoyuanx.vertxdemo.nospring.web;

import com.taoyuanx.springmvc.vertx.core.anno.RouteHandler;
import com.taoyuanx.springmvc.vertx.core.anno.RouteMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import java.util.concurrent.TimeUnit;

/**
 * @author dushitaoyuan
 * @date 2020/4/21
 */
@RouteHandler(value = "api")
public class RouteHandlerDemo {
    @RouteMapping(value = "demo", method = HttpMethod.GET)
    public Handler<RoutingContext> handle() {
        return ctx -> {
            ctx.response().end("demo");
            System.out.println("demo");
        };
    }

    @RouteMapping(value = "blockDemo", method = HttpMethod.GET, blocked = true)
    public Handler<RoutingContext> blockHande() {
        return ctx -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("blockDemo");
                ctx.response().end("blockDemo");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };
    }

    @RouteMapping(value = "interceptorDemo", method = HttpMethod.GET)
    public Handler<RoutingContext> interceptorDemo() {
        return ctx -> {
            ctx.response().setChunked(true);
            System.out.println("interceptorDemo");
            ctx.response().end("interceptorDemo");
        };
    }

    @RouteMapping(value = "error", method = HttpMethod.GET)
    public Handler<RoutingContext> errorTest() {
        return ctx -> {
            System.out.println("error");
            throw new MyException(" error MyException");
        };
    }
}
