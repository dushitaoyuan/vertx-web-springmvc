package com.taoyuanx.springmvc.vertx.core.core;

import io.vertx.core.VertxException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dushitaoyuan
 * @desc 对象工厂
 * @date 2020/4/21
 */
public interface BeanFactory {
    Object get(Class clazz);

    public static class DefaultBeanFactoryImpl implements BeanFactory {
        private Map<Class, Object> cache = new HashMap<>();

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
    }
}
