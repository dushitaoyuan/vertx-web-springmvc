package com.taoyuanx.springmvc.vertx.core.anno;

import java.lang.annotation.*;

/**
 * 异常处理注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionHandler {
    Class<? extends Throwable>[] value() default {};
}
