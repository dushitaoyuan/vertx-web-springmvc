package com.taoyuanx.springmvc.vertx.core.start;

import com.taoyuanx.springmvc.vertx.core.core.SpringMvcRouterHandler;
import com.taoyuanx.springmvc.vertx.core.core.VertxConstant;
import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import com.taoyuanx.springmvc.vertx.core.util.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.Arrays;

/**
 * @author dushitaoyuan
 * @date 2020/4/22
 */
public class VertxHttpServerVerticle extends AbstractVerticle {

    private VertxHttpServerConfig serverConfig;
    private HttpServer server;

    public HttpServer getServer() {
        return server;
    }

    public VertxHttpServerConfig getServerConfig() {
        return serverConfig;
    }

    public VertxHttpServerVerticle(VertxHttpServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();
        this.server = server;
        SpringMvcRouterHandler routerHandlerRegister = new SpringMvcRouterHandler(serverConfig.getBasePackages(), serverConfig);
        routerHandlerRegister.routerHandle();
        Router router = serverConfig.getRouter();
        if(StringUtil.isEmpty(serverConfig.getStaticDir())){
            serverConfig.setStaticDir(VertxConstant.DEALUT_STATIC_DIR);
        }
        Arrays.stream(serverConfig.getStaticDir().split(",")).forEach(staticDir->{
            router.route().handler(StaticHandler.create(staticDir));
        });
        server.requestHandler(router::handle);
        server.listen(serverConfig.getHttpPort());
    }
}
