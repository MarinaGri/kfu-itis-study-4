package ru.itis.framework.exceptions;

public class ConversionFailException extends RuntimeException {
    public ConversionFailException() {
        super();
    }

    public ConversionFailException(String message) {
        super(message);
    }

    public ConversionFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionFailException(Throwable cause) {
        super(cause);
    }

    protected ConversionFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
