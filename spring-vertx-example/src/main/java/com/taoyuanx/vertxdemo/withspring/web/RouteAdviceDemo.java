package com.taoyuanx.vertxdemo.withspring.web;

import com.taoyuanx.springmvc.vertx.core.core.exception.ExceptionHandler;
import com.taoyuanx.springmvc.vertx.core.core.exception.RouterAdvice;
import com.taoyuanx.springmvc.vertx.core.util.ResponseUtil;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

/**
 * @author dushitaoyuan
 * @date 2020/4/21
 * 异常处理
 */
@RouterAdvice
@Component
public class RouteAdviceDemo {
    @ExceptionHandler(value = MyException.class)
    public Handler<RoutingContext> handle() {
        return ctx -> {
            MyException failure = (MyException) ctx.failure();
            ResponseUtil.responseJson(ctx, 500, new JsonObject().put("errorMsg",failure.getMessage()));
        };
    }
}
