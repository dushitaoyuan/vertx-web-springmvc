package com.taoyuanx.springmvc.vertx.core.core.exception;

import java.lang.annotation.*;

/**
 * 类似 @ControlerAdvice
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RouterAdvice {

}
