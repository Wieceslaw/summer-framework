package com.github.wieceslaw.summer.exceptions;

public class ScanningException extends RuntimeException {
    public ScanningException() {
    }

    public ScanningException(String message) {
        super(message);
    }

    public ScanningException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScanningException(Throwable cause) {
        super(cause);
    }
}
