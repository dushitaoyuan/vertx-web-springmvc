package com.taoyuanx;

import com.taoyuanx.springmvc.vertx.core.anno.ExceptionHandler;
import com.taoyuanx.springmvc.vertx.core.util.ReflectionUtil;
import com.taoyuanx.springmvc.vertx.core.util.RouteUtil;
import com.taoyuanx.web.RouteAdviceDemo;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author dushitaoyuan
 * @date 2020/4/20
 */
public class RouteUtilTest {
    @Test
    public void routeTest() throws Exception {
        System.out.println(RouteUtil.calcRouteUrl("/api/", "/v1/", null, "/v3", "v2/"));
        System.out.println(RouteUtil.calcRouteUrl("api", "v1","v222"));

    }

    @Test
    public void methodHandleTest() throws Throwable {
        RouteUtil.getRouteMethodHandler(RouteUtilTest.class.getMethod("methodHandle"), new RouteUtilTest());

    }

    public Handler<RoutingContext> methodHandle() {
        return ctx -> {
            System.err.println("woshi handle！");
            ctx.next();
        };
    }

    @Test
    public void routerAdvideTest() throws Throwable {
        Set<Method> methodWithAnnotation = ReflectionUtil.getMethodWithAnnotation(RouteAdviceDemo.class, ExceptionHandler.class);
        System.out.println(methodWithAnnotation);

    }


}
