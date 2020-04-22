package com.taoyuanx.springmvc.vertx.core.anno;

import java.lang.annotation.*;

/**
 * 顺序标记
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {
    int order() default 0;

}
