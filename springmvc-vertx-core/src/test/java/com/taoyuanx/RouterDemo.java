package com.taoyuanx;

import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import com.taoyuanx.springmvc.vertx.core.start.ServerBootStart;
import com.taoyuanx.springmvc.vertx.core.start.VertxHttpServerVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;


public class RouterDemo {

    public static void main(String[] args) {
        VertxHttpServerVerticle vertxHttpServerVerticle = ServerBootStart.start("com.taoyuanx.web");
        VertxHttpServerConfig serverConfig = vertxHttpServerVerticle.getServerConfig();
        ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create(serverConfig.getVertx());
        Router router = serverConfig.getRouter();
        router.route("index").handler(ctx -> {
            JsonObject data = new JsonObject()
                    .put("hello", "dushitaoyuan say hi to you!");
            engine.render(data, "templates/index.html", res -> {
                ctx.response().end(res.result());
            });
        });

    }

}
