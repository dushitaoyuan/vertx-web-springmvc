package com.taoyuanx.springmvc.vertx.core.start;

import com.taoyuanx.springmvc.vertx.core.core.BeanFactory;
import com.taoyuanx.springmvc.vertx.core.core.VertxConstant;
import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import com.taoyuanx.springmvc.vertx.core.util.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxException;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.ext.web.Router;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author dushitaoyuan
 * @desc vertx server 启动类
 * @date 2020/4/22
 */
public class ServerBootStart {


    public static void start(String basepackages, Consumer<VertxHttpServerVerticle> consumer) {
        VertxHttpServerConfig serverConfig = new VertxHttpServerConfig();
        serverConfig.setBasePackages(basepackages);
        start(serverConfig, consumer);
    }

    public static void start(VertxHttpServerConfig serverConfig, Consumer<VertxHttpServerVerticle> consumer) {
        resolveDefaultServerConfig(serverConfig);
        VertxHttpServerVerticle vertxHttpServerVerticle = new VertxHttpServerVerticle(serverConfig);
        consumer.accept(vertxHttpServerVerticle);
        Vertx.vertx().deployVerticle(vertxHttpServerVerticle);
    }

    private static void resolveDefaultServerConfig(VertxHttpServerConfig serverConfig) {
        if (StringUtil.isEmpty(serverConfig.getBasePackages())) {
            throw new VertxException("basePackages must not null");
        }
        if (Objects.isNull(serverConfig.getBeanFactory())) {
            serverConfig.setBeanFactory(new BeanFactory.DefaultBeanFactoryImpl());
        }
        if (Objects.isNull(serverConfig.getBeanFactory())) {
            serverConfig.setHttpPort(VertxConstant.DEFAULT_SERVER_PORT);
        }
        if (Objects.isNull(serverConfig.getEventBusconnectTimeout())) {
            serverConfig.setEventBusconnectTimeout(VertxConstant.DEFAULT_EVENTBUS_CONNECTTIMEOUT);
        }
        if (Objects.isNull(serverConfig.getWorkPoolSize())) {
            serverConfig.setWorkPoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);
        }
        if (StringUtil.isEmpty(serverConfig.getStaticDir())) {
            serverConfig.setStaticDir(VertxConstant.DEALUT_STATIC_DIR);
        }
        if (Objects.isNull(serverConfig.getVertx())) {
            EventBusOptions eventBusOptions = new EventBusOptions();
            eventBusOptions.setConnectTimeout(serverConfig.getEventBusconnectTimeout());
            Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(serverConfig.getWorkPoolSize())
                    .setEventBusOptions(eventBusOptions));
            serverConfig.setVertx(vertx);
        }
        if (Objects.isNull(serverConfig.getRouter())) {
            serverConfig.setRouter(Router.router(serverConfig.getVertx()));
        }

    }


}
