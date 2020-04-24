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

import java.lang.annotation.Annotation;
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
            /**
             * 判断方法是否阻塞执行
             */
            routeInfo.setBlocked(calcMethodHandlerBlocked(method));
            if (methodHandlerHasAnnotion(method, ResponseBody.class)) {
                routeInfo.setMethodHandler(HandlerWrapper.jsonWrapper(serverConfig, method, instance));
                return;
            }
            Annotation templateBodyAnno = RouteUtil.getMethodHandlerHasAnnotion(method, TemplateBody.class);
            if (Objects.nonNull(templateBodyAnno)) {
                routeInfo.setMethodHandler(HandlerWrapper.templateWrapper(serverConfig, method, instance, (TemplateBody) templateBodyAnno));
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

    private static boolean calcMethodHandlerBlocked(Method method) {
        /**
         * 判断逻辑
         * 1.handler class 有注解 ResponseBody|TemplateBody 必须阻塞
         * 2. 否则 根据 handler method计算
         * 3. 计算逻辑:handler method 是否有注解 ResponseBody|TemplateBody,且 RouteMapping blocked 值为true
         */
        boolean classBlocked = calcClassBlocked(method.getDeclaringClass());
        if (classBlocked) {
            return true;
        } else {
            return calcMethodBlocked(method);
        }
    }

    private static boolean calcMethodBlocked(Method method) {
        return method.isAnnotationPresent(ResponseBody.class) ||
                method.isAnnotationPresent(TemplateBody.class) ||
                method.getAnnotation(RouteMapping.class).blocked();
    }

    private static boolean calcClassBlocked(Class clazz) {
        return clazz.isAnnotationPresent(ResponseBody.class) ||
                clazz.isAnnotationPresent(TemplateBody.class);
    }

    private static boolean methodHandlerHasAnnotion(Method method, Class annotationClass) {
        return method.isAnnotationPresent(annotationClass) || method.getDeclaringClass().isAnnotationPresent(annotationClass);
    }

    public static Annotation getMethodHandlerHasAnnotion(Method method, Class annotationClass) {
        Annotation annotation = method.getAnnotation(annotationClass);
        if (annotation == null) {
            return method.getDeclaringClass().getAnnotation(annotationClass);
        }
        return annotation;
    }

    public static Handler<RoutingContext> resolveHandler(Method method, Object instance) {
        try {
            return (Handler<RoutingContext>) method.invoke(instance);
        } catch (Exception e) {
            throw new VertxException("route hander error", e);
        }
    }

}
