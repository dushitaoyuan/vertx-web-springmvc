package com.taoyuanx.springmvc.vertx.core.core.message;

import com.taoyuanx.springmvc.vertx.core.core.VertxConstant;

import java.lang.annotation.*;

/**
 * @author dushitaoyuan
 * @desc 消息转换标记
 * @date 2020/4/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VertxMessageConverter {
}
