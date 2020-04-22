package com.taoyuanx.springmvc.vertx.core.core;

import com.taoyuanx.springmvc.vertx.core.anno.*;
import com.taoyuanx.springmvc.vertx.core.interceptors.IRequestInterceptor;
import com.taoyuanx.springmvc.vertx.core.util.OrderUtil;
import com.taoyuanx.springmvc.vertx.core.util.ReflectionUtil;
import com.taoyuanx.springmvc.vertx.core.util.ResponseUtil;
import com.taoyuanx.springmvc.vertx.core.util.RouteUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.VertxException;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * vertx springmvc风格适配
 */
public class SpringMvcRouterHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SpringMvcRouterHandler.class);

    // 需要扫描注册的Router路径
    private static volatile Reflections reflections;

    private static String REQUEST_PREFIX = "";

    private BeanFactory beanFactory;
    private String serverPrefix;
    private  VertxHttpServerConfig httpServerConfig;

    public SpringMvcRouterHandler(String basePackages, BeanFactory beanFactory, VertxHttpServerConfig httpServerConfig) {
        this(basePackages, REQUEST_PREFIX, beanFactory, httpServerConfig);
    }

    public SpringMvcRouterHandler(String basePackages,  VertxHttpServerConfig httpServerConfig) {
        this(basePackages, REQUEST_PREFIX, new BeanFactory.DefaultBeanFactoryImpl(), httpServerConfig);
    }

    public SpringMvcRouterHandler(String basePackages, String serverPrefix, BeanFactory beanFactory, VertxHttpServerConfig httpServerConfig) {
        Objects.requireNonNull(basePackages, "The basePackages scan is empty.");
        Objects.requireNonNull(basePackages, "The beanFactory  can't empty.");
        reflections = ReflectionUtil.getReflections(basePackages);
        this.serverPrefix = serverPrefix;
        this.beanFactory = beanFactory;
        this.httpServerConfig = httpServerConfig;
    }



    public Router routerHandle() {
        /**
         * 跨域
         */
        Router router = httpServerConfig.getRouter();
        router.route().handler(CorsHandler.create("*").allowedMethods(new HashSet<HttpMethod>() {{
            add(HttpMethod.GET);
            add(HttpMethod.POST);
            add(HttpMethod.OPTIONS);
            add(HttpMethod.PUT);
            add(HttpMethod.DELETE);
            add(HttpMethod.HEAD);
        }}));

        router.route().handler(BodyHandler.create(true));
        initInterceptor(router);
        /**
         * 前置拦截
         */
        registInterceptorPreHandler(router);

        /**
         * 路由处理
         */
        registRouterHandler(router);
        /**
         * 异常处理
         */
        registRouterExceptionHandler(router);
        /**
         * 后置拦截
         */
        registInterceptorAfterHandler(router);

        return router;
    }

    /**
     * 注册 RouterInterceptor
     *
     * @param router
     */

    private List<IRequestInterceptor> interceptorList = new ArrayList<>();

    private void initInterceptor(Router router) {
        Set<Class<? extends IRequestInterceptor>> interceptorHandlers = reflections.getSubTypesOf(IRequestInterceptor.class);
        if (Objects.isNull(interceptorHandlers) && interceptorHandlers.size() == 0) {
            return;
        }
        interceptorHandlers.stream().filter(clazz -> {
            return clazz.getAnnotation(Interceptor.class) != null && ReflectionUtil.isClassImplements(clazz, IRequestInterceptor.class);
        }).sorted(Comparator.comparingInt(x -> {
            return OrderUtil.getOrder(x, VertxConstant.INTERCEPTOR_PRE_DEFAULT_ORDER);
        })).forEach(interceptorHandler -> {
                    IRequestInterceptor requestInterceptor = (IRequestInterceptor) beanFactory.get(interceptorHandler);
                    LOG.debug("regist {}  interceptor", requestInterceptor);
                    interceptorList.add(requestInterceptor);
                }
        );
    }

    private void registInterceptorPreHandler(Router router) {
        interceptorList.stream().forEach(interceptor -> {
            String[] path = interceptor.getClass().getAnnotation(Interceptor.class).value();
            if (Objects.isNull(path) || path.length == 0) {
                doRegistInterceptorPreHandler(router, null, interceptor);
            } else {
                Arrays.stream(path).forEach(subPath -> {
                    doRegistInterceptorPreHandler(router, subPath, interceptor);
                });
            }

        });


    }

    private void doRegistInterceptorPreHandler(Router router, String path, IRequestInterceptor interceptor) {
        try {
            Route route = null;
            if (Objects.isNull(path)) {
                route = router.route();
            } else {
                route = router.route(path);
            }
            route.order(Integer.MIN_VALUE).handler(ctx -> {
                HttpServerResponse response = ctx.response();
                if (response.ended()) {
                    return;
                }
                if (response.ended()) {
                    return;
                }
                if (!interceptor.pre(ctx)) {
                    ResponseUtil.responseEnd(ctx.response());
                    return;
                }
                ctx.next();
            });

        } catch (Exception e) {
            LOG.error("regist after interceptorHandler error", e);
            throw new VertxException(e);
        }
    }

    private void registInterceptorAfterHandler(Router router) {


        List<IRequestInterceptor> afterList = new ArrayList<>(interceptorList);
        Collections.reverse(afterList);
        afterList.stream().forEach(interceptor -> {
            String[] path = interceptor.getClass().getAnnotation(Interceptor.class).value();
            if (Objects.isNull(path) || path.length == 0) {
                doRegistInterceptorAfterHandler(router, null, interceptor);
            } else {
                Arrays.stream(path).forEach(subPath -> {
                    doRegistInterceptorAfterHandler(router, subPath, interceptor);
                });
            }

        });
    }

    private void doRegistInterceptorAfterHandler(Router router, String path, IRequestInterceptor interceptor) {
        try {
            Route route = null;
            if (Objects.isNull(path)) {
                route = router.route();
            } else {
                route = router.route(path);
            }
            route.last().handler(ctx -> {
                HttpServerResponse response = ctx.response();
                if (response.ended()) {
                    return;
                }
                if (response.ended()) {
                    return;
                }
                if (!interceptor.after(ctx)) {
                    ResponseUtil.responseEnd(ctx.response());
                    return;
                }
                ctx.next();
            });

        } catch (Exception e) {
            LOG.error("regist after interceptorHandler error", e);
            throw new VertxException(e);
        }

    }


    /**
     * 注册 异常处理
     *
     * @param router
     */
    private void registRouterExceptionHandler(Router router) {
        Set<Class<?>> exceptionHandlers = reflections.getTypesAnnotatedWith(RouterAdvice.class);
        if (Objects.isNull(exceptionHandlers) && exceptionHandlers.size() == 0) {
            return;
        }
        Map<Class<? extends Throwable>, Handler<RoutingContext>> errorHandlerMaping = new HashMap<>();
        exceptionHandlers.stream().forEach(routerAdviceHandler -> {
                    Object instance = beanFactory.get(routerAdviceHandler);
                    Set<Method> exceptionHandlerMethod = ReflectionUtil.getMethodWithAnnotation(routerAdviceHandler, ExceptionHandler.class);
                    exceptionHandlerMethod.stream().forEach(method -> {
                        ExceptionHandler annotation = method.getAnnotation(ExceptionHandler.class);
                        Handler<RoutingContext> errorHandler = RouteUtil.getRouteMethodHandler(method, instance);
                        Class<? extends Throwable>[] value = annotation.value();
                        if (Objects.nonNull(value) && value.length > 0) {
                            Arrays.stream(value).forEach(error -> {
                                LOG.debug("regist error  handler for error {} ", error);
                                if (errorHandlerMaping.put(error, errorHandler) != null) {
                                    LOG.warn("{} error  override ", error);
                                }
                            });

                        }

                    });
                }
        );

        try {
            if (!errorHandlerMaping.isEmpty()) {
                router.route().failureHandler(ctx -> {
                    Throwable failure = ctx.failure();
                    /**
                     * 查找对应的异常处理handler
                     */
                    Handler<RoutingContext> handler = errorHandlerMaping.get(failure);
                    if (Objects.isNull(handler)) {
                        for (Class clazz : errorHandlerMaping.keySet()) {
                            if (clazz.isInstance(failure)) {
                                handler = errorHandlerMaping.get(clazz);
                                break;
                            }
                        }
                    }
                    if (Objects.nonNull(handler)) {
                        handler.handle(ctx);
                        return;
                    }
                    LOG.warn("can't find  error handler for {} error, handle for default", failure.getClass());
                    JsonObject errorResult = new JsonObject();
                    errorResult.put("errorMsg", failure.getMessage());
                    ResponseUtil.responseJson(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), errorResult);
                });
            }
        } catch (Exception e) {
            LOG.error("regist exceptionHandler error", e);
            throw new VertxException(e);
        }
    }

    /**
     * 注册 routehandler
     *
     * @param router
     * @throws Exception
     */
    private void registRouterHandler(Router router) {
        Set<Class<?>> routerHandlers = reflections.getTypesAnnotatedWith(RouteHandler.class);
        if (Objects.isNull(routerHandlers) && routerHandlers.size() == 0) {
            return;
        }
        routerHandlers.stream().forEach(handler -> {
                    try {
                        List<RouteInfo> routeInfoList = extractRouteInfo(handler);
                        routeInfoList.stream().sorted(Comparator.comparingInt(RouteInfo::getOrder)).forEach(routeInfo -> {

                            String routePath = routeInfo.getRoutePath();
                            List<HttpMethod> routeMethod = routeInfo.getRouteMethod();
                            Handler<RoutingContext> methodHandler = routeInfo.getMethodHandler();
                            if (Objects.isNull(routeMethod) || routeMethod.size() == 0) {
                                LOG.debug("regist route [path:->{}] ", routePath);
                                Route route = router.route(routePath);
                                if (routeInfo.isBlocked()) {
                                    route.blockingHandler(methodHandler);
                                } else {
                                    route.handler(methodHandler);
                                }
                                return;
                            }
                            routeMethod.stream().forEach(httpMethod -> {
                                Route route = router.route(httpMethod, routePath);
                                LOG.debug("regist route [path:->{}, method->{}] ", routePath, httpMethod);
                                if (routeInfo.isBlocked()) {
                                    route.blockingHandler(methodHandler);
                                } else {
                                    route.handler(methodHandler);
                                }
                                String[] consumes = routeInfo.getConsumes();
                                if (Objects.nonNull(consumes)) {
                                    Arrays.stream(consumes).forEach(route::consumes);
                                }
                                String[] produces = routeInfo.getProduces();
                                if (Objects.nonNull(produces)) {
                                    Arrays.stream(produces).forEach(route::produces);
                                }
                            });
                        });
                    } catch (Exception e) {
                        LOG.error("regist router handler error", e);
                        throw new VertxException(e);
                    }
                }

        );

    }

    private List<RouteInfo> extractRouteInfo(Class routeHandlerClass) throws Exception {

        RouteHandler routeHandlerAnnotation = RouteUtil.getRouteHandlerAnnotation(routeHandlerClass);
        if (Objects.isNull(routeHandlerAnnotation)) {
            throw new VertxException("@RouteHandler  must need");
        }
        String routePrefix = routeHandlerAnnotation.value();
        Object instance = this.beanFactory.get(routeHandlerClass);
        List<RouteInfo> routeInfoList = Stream.of(routeHandlerClass.getMethods()).filter(method -> method.isAnnotationPresent(RouteMapping.class)
        ).map(method -> {
            RouteMapping routeMapping = RouteUtil.getRouteMappingAnnotation(method);
            HttpMethod[] routeMethod = routeMapping.method();
            String routePath = RouteUtil.calcRouteUrl(this.serverPrefix, routePrefix, routeMapping.value());
            Handler<RoutingContext> methodHandler = RouteUtil.getRouteMethodHandler(method, instance);
            RouteInfo routeInfo = new RouteInfo();
            routeInfo.setRoutePath(routePath);
            routeInfo.setRouteMethod(Arrays.asList(routeMethod));
            routeInfo.setOrder(routeMapping.order());
            routeInfo.setConsumes(routeMapping.consumes());
            routeInfo.setProduces(routeMapping.produces());
            routeInfo.setBlocked(routeMapping.blocked());
            routeInfo.setMethodHandler(methodHandler);
            return routeInfo;
        }).collect(Collectors.toList());
        return routeInfoList;

    }


}
