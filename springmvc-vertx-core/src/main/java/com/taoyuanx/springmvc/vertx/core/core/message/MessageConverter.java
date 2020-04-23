package com.taoyuanx.springmvc.vertx.core.core.message;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * @author dushitaoyuan
 * @desc 消息转换
 * @date 2020/4/23
 */
public interface MessageConverter {
    boolean support(Object source);

    Handler<RoutingContext> convertTo(Object source);

}
