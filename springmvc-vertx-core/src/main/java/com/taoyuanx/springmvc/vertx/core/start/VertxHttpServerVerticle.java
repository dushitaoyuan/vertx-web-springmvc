package com.taoyuanx.springmvc.vertx.core.start;

import com.taoyuanx.springmvc.vertx.core.core.SpringMvcRouterHandler;
import com.taoyuanx.springmvc.vertx.core.core.VertxConstant;
import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import com.taoyuanx.springmvc.vertx.core.core.message.JsonMessageConverter;
import com.taoyuanx.springmvc.vertx.core.util.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author dushitaoyuan
 * @date 2020/4/22
 */
public class VertxHttpServerVerticle extends AbstractVerticle {

    private HttpServer server;

    public HttpServer getServer() {
        return server;
    }


    private Consumer<SpringMvcRouterHandler> after;
    private SpringMvcRouterHandler routerHandlerRegister;

    public VertxHttpServerVerticle(SpringMvcRouterHandler routerHandlerRegister, Consumer<SpringMvcRouterHandler> after) {
        this.after = after;
        this.routerHandlerRegister = routerHandlerRegister;
    }

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();
        this.server = server;
        VertxHttpServerConfig serverConfig = routerHandlerRegister.getHttpServerConfig();
        routerHandlerRegister.routerHandle();
        after.accept(routerHandlerRegister);
        /**
         * 注册默认json转换
         */
        routerHandlerRegister.registVertxMessageConverter(new JsonMessageConverter());
        Router router = serverConfig.getRouter();
        Arrays.stream(serverConfig.getStaticDir().split(",")).forEach(staticDir -> {
            router.route().handler(StaticHandler.create(staticDir));
        });
        this.server.requestHandler(router::handle);
        this.server.listen(serverConfig.getHttpPort());
    }
}
