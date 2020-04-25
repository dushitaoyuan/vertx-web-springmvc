package com.taoyuanx.vertxdemo.withspring;

import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import com.taoyuanx.springmvc.vertx.core.core.spring.SpringBeanFactory;
import com.taoyuanx.springmvc.vertx.core.start.ServerBootStart;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class WithSpringBootDemo {

    public static void main(String[] args) {
        VertxHttpServerConfig vertxHttpServerConfig = new VertxHttpServerConfig();
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringBeanFactory springBeanFactory = applicationContext.getBean(SpringBeanFactory.class);
        vertxHttpServerConfig.setBeanFactory(springBeanFactory);
        ServerBootStart.start("com.taoyuanx.vertxdemo.withspring.web", 8081, (springMvcRouterHandler) -> {
            System.out.println("启动前");
        }, (springMvcRouterHandler) -> {
            VertxHttpServerConfig serverConfig = springMvcRouterHandler.getHttpServerConfig();
            ThymeleafTemplateEngine thymeleafTemplateEngine = ThymeleafTemplateEngine.create(serverConfig.getVertx());
            FreeMarkerTemplateEngine freeMarkerTemplateEngine = FreeMarkerTemplateEngine.create(serverConfig.getVertx());
            springMvcRouterHandler.registVertxTemplateEngine("myTemplate", "templates/", "html", thymeleafTemplateEngine);
            springMvcRouterHandler.registVertxTemplateEngine("myTemplate2", "templates/", "ftl", freeMarkerTemplateEngine);
            System.out.println("启动后");
        });


    }

}
