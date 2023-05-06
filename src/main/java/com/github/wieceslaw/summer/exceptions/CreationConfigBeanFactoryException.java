package com.github.wieceslaw.summer.exceptions;

public class CreationConfigBeanFactoryException extends RuntimeException {
    public CreationConfigBeanFactoryException() {
    }

    public CreationConfigBeanFactoryException(String message) {
        super(message);
    }

    public CreationConfigBeanFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreationConfigBeanFactoryException(Throwable cause) {
        super(cause);
    }
}
