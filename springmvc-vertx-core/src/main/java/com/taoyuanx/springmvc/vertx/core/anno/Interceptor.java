package com.taoyuanx.springmvc.vertx.core.anno;


import java.lang.annotation.*;

/**
 * 拦截器注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Interceptor {
    /**
     * 拦截路径
     */
    String[] value() default {};

}
