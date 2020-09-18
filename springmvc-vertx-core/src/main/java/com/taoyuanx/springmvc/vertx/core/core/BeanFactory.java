package com.taoyuanx.springmvc.vertx.core.core;

import com.taoyuanx.springmvc.vertx.core.core.exception.RouterAdvice;
import com.taoyuanx.springmvc.vertx.core.util.ReflectionUtil;
import io.vertx.core.VertxException;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author dushitaoyuan
 * @desc 对象工厂
 * @date 2020/4/21
 */
public interface BeanFactory {
    Object get(Class clazz);

    Set<Object> getTypesAnnotatedWith(Class annotion);


}
