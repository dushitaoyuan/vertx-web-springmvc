package com.taoyuanx.springmvc.vertx.core.core;

import com.taoyuanx.springmvc.vertx.core.util.ReflectionUtil;
import io.vertx.core.VertxException;
import org.reflections.Reflections;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultBeanFactoryImpl implements BeanFactory {
    private Map<Class, Object> cache = new HashMap<>();
    private Reflections reflections;

    public DefaultBeanFactoryImpl(String basePackages) {
        this.reflections = ReflectionUtil.getReflections(basePackages);
    }

    @Override
    public synchronized Object get(Class clazz) {
        try {
            if (!cache.containsKey(clazz)) {
                cache.put(clazz, clazz.newInstance());
            }
            return cache.get(clazz);
        } catch (Exception e) {
            throw new VertxException(clazz + "bean get error");
        }
    }

    @Override
    public Set<Object> getTypesAnnotatedWith(Class annotion) {
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(annotion);
        if (Objects.isNull(types)) {
            return Collections.EMPTY_SET;
        }
        return types.stream().map(this::get).collect(Collectors.toSet());
    }
}