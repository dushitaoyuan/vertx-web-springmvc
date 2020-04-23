package com.taoyuanx.springmvc.vertx.core.anno.route;

import com.taoyuanx.springmvc.vertx.core.core.VertxConstant;
import io.vertx.core.http.HttpMethod;

import java.lang.annotation.*;

/**
 * 类似 @RequestMapping
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RouteMapping {
    String value() default "";

    HttpMethod[] method() default {};

    int order() default VertxConstant.AUTO_REGIST_ROUTE_ORDER;

    boolean blocked() default false;

    String[] consumes() default {};

    String[] produces() default {};

    String desc() default "";
}
