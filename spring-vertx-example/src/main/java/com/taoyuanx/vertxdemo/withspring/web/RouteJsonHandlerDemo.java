package com.taoyuanx.vertxdemo.withspring.web;

import com.taoyuanx.springmvc.vertx.core.anno.route.ResponseBody;
import com.taoyuanx.springmvc.vertx.core.anno.route.RouteHandler;
import com.taoyuanx.springmvc.vertx.core.anno.route.RouteMapping;
import com.taoyuanx.springmvc.vertx.core.core.template.TemplateBody;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

/**
 * @author dushitaoyuan
 * @date 2020/4/21
 */
@RouteHandler(value = "json")
@ResponseBody
@Component
public class RouteJsonHandlerDemo {

    @RouteMapping(value = "jsonMessage", method = HttpMethod.GET)
    public JsonObject jsonMessage(RoutingContext ctx) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.put("spring json","json");
        return jsonObject;
    }

}
