package com.taoyuanx.springmvc.vertx.core.core;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dushitaoyuan
 * @date 2020/4/20
 */
public class VertxHolder {
    private Vertx vertx;
    private Router router;
    private Map<String, Object> data;

    public VertxHolder() {
        this.data = new ConcurrentHashMap<>();
    }

    private static final Map<String, VertxHolder> CONTEXT_HOLDER = new ConcurrentHashMap<>();

    public static VertxHolder putContextHolder(String contextId, VertxHolder vertxHolder) {
        return CONTEXT_HOLDER.put(contextId, vertxHolder);
    }

    public static VertxHolder getContextHolder(String contextId) {
        return CONTEXT_HOLDER.get(contextId);
    }

    public static boolean hasContextHolder(String contextId) {
        return CONTEXT_HOLDER.containsKey(contextId);
    }

    public Vertx getVertx() {
        return vertx;
    }

    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public VertxHolder put(String key, Object data) {
        this.data.put(key, data);
        return this;
    }

    public <T> T get(String key) {
        return (T) this.data.get(key);
    }
}
