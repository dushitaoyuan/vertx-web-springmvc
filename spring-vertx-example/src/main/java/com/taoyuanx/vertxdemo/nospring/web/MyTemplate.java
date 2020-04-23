package com.taoyuanx.vertxdemo.nospring.web;

import com.taoyuanx.springmvc.vertx.core.core.template.AbstractTemplateEngineDelegate;
import com.taoyuanx.springmvc.vertx.core.core.template.VertxTemplateEngine;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;

/**
 * @author dushitaoyuan
 * @desc 模板引擎
 * @date 2020/4/23
 */
@VertxTemplateEngine(name = "myTemplate")
public class MyTemplate extends AbstractTemplateEngineDelegate {
    @Override
    public boolean support(String suffix) {
        return suffix.equals("html");
    }

    @Override
    public void after() {
        this.delegate = ThymeleafTemplateEngine.create(vertx);
    }
}
