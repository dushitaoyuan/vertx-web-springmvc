package com.taoyuanx.springmvc.vertx.core.util;


import com.taoyuanx.springmvc.vertx.core.anno.RouteHandler;
import com.taoyuanx.springmvc.vertx.core.anno.RouteMapping;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author dushitaoyuan
 * @date 2020/4/20
 */
public class RouteUtil {

    public static final String calcRouteUrl(String prefix, String... path) {
        StringBuilder routeUrl = new StringBuilder();
        if (StringUtil.isNotEmpty(prefix)) {
            routeUrl.append("/").append(routeSubPath(prefix));
        }
        Arrays.stream(path).filter(StringUtil::isNotEmpty).forEach(subPath -> {
            routeUrl.append("/");
            routeUrl.append(routeSubPath(subPath));
        });
        return routeUrl.toString();
    }

    private static final String routeSubPath(String path) {

        if (path.startsWith("/")) {
            path = path.replaceFirst("/", "");
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    public static RouteHandler getRouteHandlerAnnotation(Class routeHandlerClass) {
        if (routeHandlerClass.isAnnotationPresent(RouteHandler.class)) {
            return (RouteHandler) routeHandlerClass.getAnnotation(RouteHandler.class);
        }
        return null;
    }

    public static RouteMapping getRouteMappingAnnotation(Method routerHandlerMethod) {
        if (routerHandlerMethod.isAnnotationPresent(RouteMapping.class)) {
            return (RouteMapping) routerHandlerMethod.getAnnotation(RouteMapping.class);
        }
        return null;
    }

    public static Handler<RoutingContext> getRouteMethodHandler(Method method, Object instance) {
        try {
            return (Handler<RoutingContext>) method.invoke(instance);
        } catch (Exception e) {
            throw new RuntimeException("route hander can't find", e);
        }
    }


}
