package com.taoyuanx.springmvc.vertx.core.core;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.Data;

/**
 * @author dushitaoyuan
 * @desc http server配置
 * @date 2020/4/22
 */
@Data
public class VertxHttpServerConfig {
    private Integer httpPort = 8080;
    private Integer workPoolSize;
    private Integer eventBusconnectTimeout;
    private Vertx vertx;
    private Router router;
    private String staticDir;
    /**
     * , split
     */
    private String basePackages;

    private BeanFactory beanFactory;
}
