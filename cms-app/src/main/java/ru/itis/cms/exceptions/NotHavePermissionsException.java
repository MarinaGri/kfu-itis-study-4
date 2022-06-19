package ru.itis.cms.exceptions;

public class NotHavePermissionsException extends RuntimeException {

    public NotHavePermissionsException(String message) {
        super(message);
    }
}
