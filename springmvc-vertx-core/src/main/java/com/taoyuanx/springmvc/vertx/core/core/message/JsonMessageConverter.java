package com.taoyuanx.springmvc.vertx.core.core.message;

import com.taoyuanx.springmvc.vertx.core.util.JSONUtil;
import com.taoyuanx.springmvc.vertx.core.util.ResponseUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpStatusClass;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * @author dushitaoyuan
 * @desc 消息转换
 * @date 2020/4/23
 */
@VertxMessageConverter
public class JsonMessageConverter implements MessageConverter {


    @Override
    public boolean support(Object source) {
        return true;
    }

    @Override
    public Handler<RoutingContext> convertTo(Object source) {
        return ctx -> {
            if (source instanceof JsonObject) {
                ResponseUtil.responseJson(ctx, HttpResponseStatus.OK.code(), ((JsonObject) source).encode());
                return;
            }
            ResponseUtil.responseJson(ctx, HttpResponseStatus.OK.code(), JSONUtil.toJsonString(source));
        };
    }
}
