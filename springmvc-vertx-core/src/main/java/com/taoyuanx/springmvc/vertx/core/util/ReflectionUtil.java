package com.taoyuanx.springmvc.vertx.core.util;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author Xu Haidong
 * @date 2018/8/16
 */
public final class ReflectionUtil {

    public static Reflections getReflections(String packageAddress) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        FilterBuilder filterBuilder = new FilterBuilder();
        String[] addresses = packageAddress.split(",");
        Stream.of(addresses).forEach(str -> configurationBuilder.addUrls(ClasspathHelper.forPackage(str.trim())));
        filterBuilder.includePackage(addresses);
        configurationBuilder.filterInputsBy(filterBuilder);
        return new Reflections(configurationBuilder);
    }

    public static Reflections getReflections(List<String> packageAddresses) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        FilterBuilder filterBuilder = new FilterBuilder();
        packageAddresses.forEach(str -> {
            configurationBuilder.addUrls(ClasspathHelper.forPackage(str.trim()));
            filterBuilder.includePackage(str.trim());
        });
        configurationBuilder.filterInputsBy(filterBuilder);
        return new Reflections(configurationBuilder);
    }

    public static Set<Method> getMethodWithAnnotation(Class clazz, Class annotation) {
        Method[] methods = clazz.getMethods();
        if (Objects.isNull(methods) && methods.length == 0) {
            return Collections.EMPTY_SET;
        }
        Set methodSet = new HashSet(methods.length);
        Arrays.stream(methods).filter(method -> method.isAnnotationPresent(annotation)
        ).forEach(method -> {
            methodSet.add(method);
        });
        return methodSet;
    }

    public static boolean isClassImplements(Class clazz, Class interfaceClass) {
        Class[] interfaces = clazz.getInterfaces();
        if (Objects.nonNull(interfaces) && interfaces.length > 0) {
            for (Class temp : interfaces) {
                if (temp.equals(interfaceClass)) {
                    return true;
                }
            }
        }
        return false;
    }




}
