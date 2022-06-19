package ru.kpfu.springmessages.exception;

public class IllegalHostException extends RuntimeException{
    public IllegalHostException() {
        super();
    }

    public IllegalHostException(String message) {
        super(message);
    }

    public IllegalHostException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalHostException(Throwable cause) {
        super(cause);
    }

    protected IllegalHostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
