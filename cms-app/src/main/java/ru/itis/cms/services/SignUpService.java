package ru.itis.cms.services;

import ru.itis.cms.dto.form.SignUpForm;
import ru.itis.cms.exceptions.DuplicateEmailException;

public interface SignUpService {

    void signUp(SignUpForm form) throws DuplicateEmailException;
}
