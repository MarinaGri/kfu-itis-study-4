package ru.kpfu.springmessages.exception;

public class EmptyRequiredDataException extends RuntimeException{
    public EmptyRequiredDataException() {
        super();
    }

    public EmptyRequiredDataException(String message) {
        super(message);
    }

    public EmptyRequiredDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRequiredDataException(Throwable cause) {
        super(cause);
    }

    protected EmptyRequiredDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
