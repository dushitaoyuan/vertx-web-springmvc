package com.taoyuanx.springmvc.vertx.core.util;


import com.taoyuanx.springmvc.vertx.core.anno.route.ResponseBody;
import com.taoyuanx.springmvc.vertx.core.anno.route.RouteHandler;
import com.taoyuanx.springmvc.vertx.core.anno.route.RouteMapping;
import com.taoyuanx.springmvc.vertx.core.core.template.TemplateBody;
import com.taoyuanx.springmvc.vertx.core.core.route.HandlerWrapper;
import com.taoyuanx.springmvc.vertx.core.core.route.RouteInfo;
import com.taoyuanx.springmvc.vertx.core.core.VertxHttpServerConfig;
import io.vertx.core.Handler;
import io.vertx.core.VertxException;
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

    public static void resolveRouteMethodHandler(Method method, Object instance,
                                                 VertxHttpServerConfig serverConfig,
                                                 RouteInfo routeInfo) {
        try {

            if (method.isAnnotationPresent(ResponseBody.class)) {
                /**
                 * 注解 blocked 失效 强制阻塞执行
                 */
                routeInfo.setBlocked(true);
                routeInfo.setMethodHandler(HandlerWrapper.jsonWrapper(serverConfig, method, instance));
                return;
            }
            if (method.isAnnotationPresent(TemplateBody.class)) {
                /**
                 * 注解 blocked 失效 强制阻塞执行
                 */
                routeInfo.setBlocked(true);
                routeInfo.setMethodHandler(HandlerWrapper.templateWrapper(serverConfig, method, instance));
                return;
            }
            Object result = method.invoke(instance);
            if (result instanceof Handler) {
                routeInfo.setMethodHandler((Handler<RoutingContext>) result);
                return;
            }

        } catch (Exception e) {
            throw new VertxException("route hander error", e);
        }
        throw new VertxException("route hander not support");
    }

    public static Handler<RoutingContext> resolveHandler(Method method, Object instance) {
        try {
            return (Handler<RoutingContext>) method.invoke(instance);
        } catch (Exception e) {
            throw new VertxException("route hander error", e);
        }
    }

}
