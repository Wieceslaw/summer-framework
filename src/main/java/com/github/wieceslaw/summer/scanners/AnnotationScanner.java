package com.github.wieceslaw.summer.scanners;

import com.github.wieceslaw.summer.annotations.Config;

import java.lang.annotation.Annotation;

public class AnnotationScanner {
    public void getClassAnnotations(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();
    }

    public void getClassAnnotationsWithSuperClasses(Class<?> clazz) {
    }

    public boolean isConfig(Class<?> clazz) {
        return clazz.isAnnotationPresent(Config.class);
    }
}
