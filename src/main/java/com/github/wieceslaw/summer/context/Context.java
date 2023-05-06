package com.github.wieceslaw.summer.context;

import com.github.wieceslaw.summer.exceptions.ScanningException;
import com.github.wieceslaw.summer.annotations.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class Context {
    private Map<String, Object> container;
    private Class<?> mainConfig;
    private Set<Class<?>> configs = new HashSet<>();

    public Context(Class<?> mainConfig) {
        this.mainConfig = mainConfig;
    }

    public void setUp() {
        assert mainConfig.isAnnotationPresent(Config.class);
        getAllConfigs(mainConfig);
        Map<String, BeanDefinition> configBeanDefinitions = new HashMap<>();
        for (Class<?> config : configs) {
            configBeanDefinitions.putAll(getConfigBeanDefinitions(config));
        }
        BeansGraphResolver beansGraphResolver = new BeansGraphResolver(configBeanDefinitions);
        List<BeanDefinition> beanCreationOrder = beansGraphResolver.resolve();
        BeanFactory beanFactory = new BeanFactory(beanCreationOrder, configBeanDefinitions);
        container = beanFactory.createBeans();
    }

    private void getAllConfigs(Class<?> config) {
        if (!config.isAnnotationPresent(Config.class)) {
            throw new ScanningException("Not a config class: {%s}".formatted(config.toString()));
        }
        if (configs.contains(config)) {
            throw new ScanningException("Cyclic config dependence: {%s}".formatted(config.toString()));
        }
        configs.add(config);
        if (config.isAnnotationPresent(Extend.class)) {
            Extend annotation = config.getAnnotation(Extend.class);
            Class<?>[] extendingConfigs = annotation.value();
            for (Class<?> extendingConfig : extendingConfigs) {
                getAllConfigs(extendingConfig);
            }
        }
    }

    private Map<String, BeanDefinition> getConfigBeanDefinitions(Class<?> config) {
        Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
        for (Method method : config.getMethods()) {
            Bean annotation = method.getAnnotation(Bean.class);
            if (annotation != null) {
                Class<?> beanClass = method.getReturnType();
                String beanId = annotation.value();
                BeanScope beanScope = BeanScope.SINGLETON;
                List<String> parameterBeanIds = new ArrayList<>();
                if (beanId.isEmpty()) {
                    beanId = beanClass.getName();
                }
                for (Parameter parameter : method.getParameters()) {
                    String parameterBeanId;
                    if (parameter.isAnnotationPresent(Qualifier.class)) {
                        Qualifier qualifier = parameter.getAnnotation(Qualifier.class);
                        parameterBeanId = qualifier.value();
                    } else {
                        parameterBeanId = parameter.getType().getName();
                    }
                    parameterBeanIds.add(parameterBeanId);
                }
                if (method.isAnnotationPresent(Scope.class)) {
                    beanScope = method.getAnnotation(Scope.class).value();
                }
                BeanDefinition beanDefinition =
                        new BeanDefinition(beanClass, beanId, beanScope, parameterBeanIds, method);
                beanDefinitions.put(beanId, beanDefinition);
            }
        }
        return beanDefinitions;
    }

    public Map<String, Object> getContainer() {
        return container;
    }
}
