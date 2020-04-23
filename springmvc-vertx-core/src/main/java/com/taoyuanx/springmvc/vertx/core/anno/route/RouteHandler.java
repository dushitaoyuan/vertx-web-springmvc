package com.taoyuanx.springmvc.vertx.core.anno.route;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由处理类标记注解 类似 @Controller
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteHandler {
    /**
     * 父级路径
     */
    String value() default "";

    /**
     * 是否阻塞
     */
    boolean blocked() default false;
}
