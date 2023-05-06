package com.github.wieceslaw.summer.context;

import java.lang.reflect.Method;
import java.util.List;

public class BeanDefinition {
    private final Class<?> clazz;
    private final String id;
    private final BeanScope scope;
    private final List<String> dependsOn;
    private final Method factoryMethod;

    public BeanDefinition(Class<?> clazz, String id, BeanScope scope, List<String> dependsOn, Method factoryMethod) {
        this.clazz = clazz;
        this.id = id;
        this.scope = scope;
        this.dependsOn = dependsOn;
        this.factoryMethod = factoryMethod;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getId() {
        return id;
    }

    public BeanScope getScope() {
        return scope;
    }

    public List<String> getDependsOn() {
        return dependsOn;
    }

    public Method getFactoryMethod() {
        return factoryMethod;
    }
}
