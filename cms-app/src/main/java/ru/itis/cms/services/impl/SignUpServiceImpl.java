package ru.itis.cms.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.cms.dto.form.SignUpForm;
import ru.itis.cms.exceptions.DuplicateEmailException;
import ru.itis.cms.exceptions.UserAuthNotFoundException;
import ru.itis.cms.models.Role;
import ru.itis.cms.models.User;
import ru.itis.cms.repositories.UserAuthoritiesRepository;
import ru.itis.cms.repositories.UsersRepository;
import ru.itis.cms.services.SignUpService;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    private final UserAuthoritiesRepository userAuthRepository;

    @Override
    public void signUp(SignUpForm form) throws DuplicateEmailException {
        if(usersRepository.findByEmail(form.getEmail()).isPresent()){
            throw new DuplicateEmailException("User with " + form.getEmail() + " email already exists");
        }


        User user = User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .password(passwordEncoder.encode(form.getPassword()))
                .email(form.getEmail())
                .authority(userAuthRepository.findByAuthority(Role.ROLE_USER)
                        .orElseThrow(UserAuthNotFoundException::new))
                .build();

        usersRepository.save(user);
    }
}
