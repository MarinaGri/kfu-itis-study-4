package ru.itis.framework.exceptions;

public class NotInitializedContextException extends RuntimeException {
    public NotInitializedContextException() {
        super();
    }

    public NotInitializedContextException(String message) {
        super(message);
    }

    public NotInitializedContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotInitializedContextException(Throwable cause) {
        super(cause);
    }

    protected NotInitializedContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
