package com.taoyuanx.vertxdemo.nospring.web;

import com.taoyuanx.springmvc.vertx.core.anno.route.ResponseBody;
import com.taoyuanx.springmvc.vertx.core.anno.route.RouteHandler;
import com.taoyuanx.springmvc.vertx.core.anno.route.RouteMapping;
import com.taoyuanx.springmvc.vertx.core.core.template.TemplateBody;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;
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

    @RouteMapping(value = "message", method = HttpMethod.GET)
    @ResponseBody
    public String message(RoutingContext ctx) {
        return "json";
    }

    @RouteMapping(value = "jsonMessage", method = HttpMethod.GET)
    @ResponseBody
    public JsonObject jsonMessage(RoutingContext ctx) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.put("json","json");
        return jsonObject;
    }

    @RouteMapping(value = "template", method = HttpMethod.GET)
    @TemplateBody
    public String template(RoutingContext ctx,JsonObject dataModel)
    {
        dataModel.put("hello","dushitaoyuan say hi to you! thymeleaf");
        return "index.html";
    }

    @RouteMapping(value = "template2", method = HttpMethod.GET)
    @TemplateBody
    public String template2(RoutingContext ctx,JsonObject dataModel)
    {
        dataModel.put("hello","dushitaoyuan say hi to you! freemarker");
        return "index.ftl";
    }
}
