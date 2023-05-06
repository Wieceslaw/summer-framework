package com.github.wieceslaw.summer.exceptions;

public class BeanCreationException extends RuntimeException {
    public BeanCreationException() {
    }

    public BeanCreationException(String beanId) {
        super("Error creating bean: {%s}".formatted(beanId));
    }

    public BeanCreationException(String beanId, Throwable cause) {
        super("Error creating bean: {%s}".formatted(beanId), cause);
    }

    public BeanCreationException(Throwable cause) {
        super(cause);
    }
}
