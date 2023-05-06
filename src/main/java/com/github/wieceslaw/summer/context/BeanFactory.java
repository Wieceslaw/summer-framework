package com.github.wieceslaw.summer.context;

import com.github.wieceslaw.summer.exceptions.BeanCreationException;
import com.github.wieceslaw.summer.exceptions.CreationConfigBeanFactoryException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {
    private final List<BeanDefinition> beanCreationOrder;
    private final Map<String, BeanDefinition> beanDefinitions;
    private final Map<Class<?>, Object> beanFactories;
    private final Map<String, Object> container;

    public BeanFactory(List<BeanDefinition> beanCreationOrder, Map<String, BeanDefinition> beanDefinitions) {
        this.beanCreationOrder = beanCreationOrder;
        this.beanDefinitions = beanDefinitions;
        this.beanFactories = new HashMap<>();
        this.container = new HashMap<>();
    }

    public Map<String, Object> createBeans() {
        for (BeanDefinition beanDefinition : beanCreationOrder) {
            if (beanDefinition.getScope() == BeanScope.SINGLETON) {
                String beanId = beanDefinition.getId();
                Object bean = createBean(beanId);
                container.put(beanId, bean);
            }
        }
        return container;
    }

    private Object createBean(String beanId) {
        BeanDefinition beanDefinition = beanDefinitions.get(beanId);
        Method factoryMethod = beanDefinition.getFactoryMethod();
        Object[] dependencies = collectBeanDependencies(beanDefinition);
        return invokeMethod(factoryMethod, dependencies);
    }

    private Object invokeMethod(Method method, Object[] parameters) {
        Class<?> factoryClass = method.getDeclaringClass();
        if (!beanFactories.containsKey(factoryClass)) {
            beanFactories.put(factoryClass, createFactoryInstance(factoryClass));
        }
        try {
            return method.invoke(beanFactories.get(factoryClass), parameters);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new BeanCreationException(e);
        }
    }

    private Object[] collectBeanDependencies(BeanDefinition beanDefinition) {
        List<Object> dependencies = new ArrayList<>();
        for (String beanId : beanDefinition.getDependsOn()) {
            if (container.containsKey(beanId)) {
                dependencies.add(container.get(beanId));
            } else {
                dependencies.add(createBean(beanId));
            }
        }
        return dependencies.toArray();
    }

    private Object createFactoryInstance(Class<?> factoryClass) {
        try {
            Constructor<?>[] declaredConstructors = factoryClass.getDeclaredConstructors();
            Constructor<?> constructor = declaredConstructors[0];
            return constructor.newInstance();
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new CreationConfigBeanFactoryException(e);
        }
    }
}
