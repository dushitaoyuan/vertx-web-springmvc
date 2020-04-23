package com.taoyuanx.vertxdemo.nospring.web;

import com.taoyuanx.springmvc.vertx.core.core.message.MessageConverter;
import com.taoyuanx.springmvc.vertx.core.core.message.VertxMessageConverter;
import com.taoyuanx.springmvc.vertx.core.core.route.HandlerWrapper;
import com.taoyuanx.springmvc.vertx.core.core.template.AbstractTemplateEngineDelegate;
import com.taoyuanx.springmvc.vertx.core.core.template.VertxTemplateEngine;
import com.taoyuanx.springmvc.vertx.core.util.JSONUtil;
import com.taoyuanx.springmvc.vertx.core.util.ResponseUtil;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * @author dushitaoyuan
 * @desc 消息转换
 * @date 2020/4/23
 */
@VertxMessageConverter
public class MyJsonMessageConverter implements MessageConverter {


    @Override
    public boolean support(Object source) {
        return true;
    }

    @Override
    public Handler<RoutingContext> convertTo(Object source) {
        return ctx -> {
            if (source instanceof JsonObject) {
                ResponseUtil.responseJson(ctx, 200, ((JsonObject) source).encode());
                return;
            }
            ResponseUtil.responseJson(ctx, 200, JSONUtil.toJsonString(source));
        };
    }
}
