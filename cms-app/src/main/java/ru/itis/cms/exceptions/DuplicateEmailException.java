package ru.itis.cms.exceptions;

public class DuplicateEmailException extends DuplicateException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
