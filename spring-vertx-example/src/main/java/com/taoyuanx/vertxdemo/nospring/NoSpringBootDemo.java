package com.taoyuanx.vertxdemo.nospring;

import com.taoyuanx.springmvc.vertx.core.core.VertxConstant;
import com.taoyuanx.springmvc.vertx.core.core.VertxHolder;
import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import com.taoyuanx.springmvc.vertx.core.core.template.AbstractTemplateEngineDelegate;
import com.taoyuanx.springmvc.vertx.core.start.ServerBootStart;
import com.taoyuanx.springmvc.vertx.core.start.VertxHttpServerVerticle;
import io.vertx.core.VertxException;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;


public class NoSpringBootDemo {

    public static void main(String[] args) {
        ServerBootStart.start("com.taoyuanx.vertxdemo.nospring", 8080, (springMvcRouterHandler) -> {
            System.out.println("启动前");
        }, (springMvcRouterHandler) -> {
            System.out.println("启动后");
        });


    }

}
