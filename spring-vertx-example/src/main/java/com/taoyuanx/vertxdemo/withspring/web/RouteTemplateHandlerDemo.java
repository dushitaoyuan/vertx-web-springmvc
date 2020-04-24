package com.taoyuanx.vertxdemo.withspring.web;

import com.taoyuanx.springmvc.vertx.core.anno.route.ResponseBody;
import com.taoyuanx.springmvc.vertx.core.anno.route.RouteHandler;
import com.taoyuanx.springmvc.vertx.core.anno.route.RouteMapping;
import com.taoyuanx.springmvc.vertx.core.core.template.TemplateBody;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author dushitaoyuan
 * @date 2020/4/21
 */
@RouteHandler(value = "tpl")
@TemplateBody
@Component
public class RouteTemplateHandlerDemo {

    @RouteMapping(value = "template", method = HttpMethod.GET)
    public String template(RoutingContext ctx, JsonObject dataModel) {
        dataModel.put("hello", "dushitaoyuan say hi to you! thymeleaf");
        return "index.html";
    }

    @RouteMapping(value = "template2", method = HttpMethod.GET)
    public String template2(RoutingContext ctx, JsonObject dataModel) {
        dataModel.put("hello", "dushitaoyuan say hi to you! freemarker");
        return "index.ftl";
    }
}
