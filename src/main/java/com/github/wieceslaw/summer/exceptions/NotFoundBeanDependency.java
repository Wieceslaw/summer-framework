package com.github.wieceslaw.summer.exceptions;

public class NotFoundBeanDependency extends RuntimeException {
    public NotFoundBeanDependency() {
    }

    public NotFoundBeanDependency(String beanName) {
        super("Not found bean: {%s}".formatted(beanName));
    }
}
