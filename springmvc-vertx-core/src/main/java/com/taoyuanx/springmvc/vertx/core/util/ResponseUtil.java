package com.taoyuanx.springmvc.vertx.core.util;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * @author dushitaoyuan
 * @date 2020/4/16
 */
public class ResponseUtil {

    public static void responseJson(RoutingContext routingContext, int httpcode, JsonObject result) {
        responseJson(routingContext, httpcode, result.encode());
    }

    public static void responseJson(RoutingContext routingContext, int httpcode, Object result) {
        responseJson(routingContext, httpcode, JSONUtil.toJsonString(result));
    }

    public static void responseJson(RoutingContext routingContext, int httpcode, String result) {
        routingContext.response().setStatusCode(httpcode)
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(result);
    }

    public static void responseEnd(HttpServerResponse response) {
        if (response.ended()) {
            return;
        }
        response.end();
    }
}
