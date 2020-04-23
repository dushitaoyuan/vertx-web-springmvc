package com.taoyuanx.springmvc.vertx.core.core.template;

import com.taoyuanx.springmvc.vertx.core.core.VertxConstant;
import io.vertx.ext.web.common.template.TemplateEngine;

import java.lang.annotation.*;

/**
 * @author dushitaoyuan
 * @desc 模板引擎标记
 * @date 2020/4/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VertxTemplateEngine {
    String name() default VertxConstant.DEFAULT_TEMPLATE_ENGINE_NAME;

    String basePath() default VertxConstant.DEALUT_TEMPLATE_BASE_PATH;
}
