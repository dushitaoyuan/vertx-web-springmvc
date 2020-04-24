package com.taoyuanx.springmvc.vertx.core.anno.route;

import java.lang.annotation.*;

/**
 * @author dushitaoyuan
 * @desc json结果转换 用法参见springmvc
 * @date 2020/4/23
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBody {
}
