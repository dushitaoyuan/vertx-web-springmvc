package com.taoyuanx.vertxdemo.withspring;

import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import com.taoyuanx.springmvc.vertx.core.spring.SpringBeanFactory;
import com.taoyuanx.springmvc.vertx.core.start.ServerBootStart;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class WithSpringBootDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        VertxHttpServerConfig vertxHttpServerConfig = new VertxHttpServerConfig();
        SpringBeanFactory springBeanFactory = applicationContext.getBean(SpringBeanFactory.class);
        vertxHttpServerConfig.setBeanFactory(springBeanFactory);
        ServerBootStart.start("com.taoyuanx.vertxdemo.withspring.web", (vertxHttpServerVerticle) -> {
            VertxHttpServerConfig serverConfig = vertxHttpServerVerticle.getServerConfig();
            ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create(serverConfig.getVertx());
            Router router = serverConfig.getRouter();
            router.route("/index").handler(ctx -> {
                JsonObject data = new JsonObject()
                        .put("hello", "dushitaoyuan say hi to you!");
                engine.render(data, "templates/index.html", res -> {
                    ctx.response().end(res.result());
                });
            });

        });


    }

}
