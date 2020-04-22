package com.taoyuanx.springmvc.vertx.core.core;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RouteInfo {
    private String routePath;
    private List<HttpMethod> routeMethod;
    private String[] consumes;
    private String[] produces;
    private Integer order;

    private boolean blocked;

    private Handler<RoutingContext> methodHandler;

    public Handler<RoutingContext> getMethodHandler() {
        return methodHandler;
    }

    public void setMethodHandler(Handler<RoutingContext> methodHandler) {
        this.methodHandler = methodHandler;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public static List<HttpMethod> allRouteMethod = null;

    static {
        allRouteMethod = Collections.unmodifiableList(Arrays.stream(HttpMethod.values()).collect(Collectors.toList())
        );
    }

    public RouteInfo() {

    }


    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public List<HttpMethod> getRouteMethod() {
        return routeMethod;
    }

    public void setRouteMethod(List<HttpMethod> routeMethod) {
        this.routeMethod = routeMethod;
    }


    public String[] getConsumes() {
        return consumes;
    }

    public void setConsumes(String[] consumes) {
        this.consumes = consumes;
    }

    public String[] getProduces() {
        return produces;
    }

    public void setProduces(String[] produces) {
        this.produces = produces;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}