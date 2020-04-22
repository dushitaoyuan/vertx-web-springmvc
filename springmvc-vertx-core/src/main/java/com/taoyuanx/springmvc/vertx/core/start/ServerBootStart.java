package com.taoyuanx.springmvc.vertx.core.start;

import com.taoyuanx.springmvc.vertx.core.core.VertxConstant;
import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.ext.web.Router;

import java.util.function.Function;

/**
 * @author dushitaoyuan
 * @desc vertx server 启动类
 * @date 2020/4/22
 */
public class ServerBootStart {


    public static void start(String basepackages, Function apply) {
        VertxHttpServerConfig serverConfig = new VertxHttpServerConfig();
        serverConfig.setHttpPort(VertxConstant.DEFAULT_SERVER_PORT);
        serverConfig.setEventBusconnectTimeout(VertxConstant.DEFAULT_EVENTBUS_CONNECTTIMEOUT);

        serverConfig.setWorkPoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);
        serverConfig.setStaticDir(VertxConstant.DEALUT_STATIC_DIR);
        serverConfig.setBasePackages(basepackages);
        EventBusOptions eventBusOptions = new EventBusOptions();
        eventBusOptions.setConnectTimeout(serverConfig.getEventBusconnectTimeout());
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(serverConfig.getWorkPoolSize())
                .setEventBusOptions(eventBusOptions));
        serverConfig.setVertx(vertx);
        serverConfig.setRouter(Router.router(vertx));
        serverConfig.setBasePackages(serverConfig.getBasePackages());
        start(serverConfig, apply);
    }

    public static void start(VertxHttpServerConfig serverConfig, Function apply) {
        VertxHttpServerVerticle vertxHttpServerVerticle = new VertxHttpServerVerticle(serverConfig);
        callBack(vertxHttpServerVerticle);
        Vertx.vertx().deployVerticle(vertxHttpServerVerticle);
    }

    public static void callBack(VertxHttpServerVerticle vertxHttpServerVerticle) {

    }
}
