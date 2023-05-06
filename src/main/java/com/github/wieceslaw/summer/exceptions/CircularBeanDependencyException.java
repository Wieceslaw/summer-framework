package com.github.wieceslaw.summer.exceptions;

public class CircularBeanDependencyException extends RuntimeException {
    public CircularBeanDependencyException() {
    }

    public CircularBeanDependencyException(String message) {
        super(message);
    }

    public CircularBeanDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircularBeanDependencyException(Throwable cause) {
        super(cause);
    }
}
