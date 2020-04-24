package com.taoyuanx.springmvc.vertx.core.core.spring;

import com.taoyuanx.springmvc.vertx.core.core.BeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dushitaoyuan
 * @date 2020/4/2221:24
 */
public class SpringBeanFactory implements BeanFactory, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public Object get(Class clazz) {
        return this.applicationContext.getBeansOfType(clazz);
    }

    @Override
    public Set<Object> getTypesAnnotatedWith(Class annotion) {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(annotion);
        return beansWithAnnotation.values().stream().collect(Collectors.toSet());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
