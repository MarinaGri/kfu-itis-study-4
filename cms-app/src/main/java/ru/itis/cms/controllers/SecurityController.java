package ru.itis.cms.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.cms.dto.form.SignInForm;
import ru.itis.cms.dto.form.SignUpForm;
import ru.itis.cms.exceptions.DuplicateEmailException;
import ru.itis.cms.services.SignUpService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RequiredArgsConstructor
@Controller
public class SecurityController {

    private final SignUpService signUpService;

    @GetMapping("/signUp")
    public String getSingUpPage(Authentication authentication, ModelMap map) {
        if (authentication != null) {
            return "redirect:articles";
        }
        map.put("form", new SignUpForm());
        return "sign_up";
    }

    @PostMapping("/signUp")
    @PreAuthorize("isAnonymous()")
    public String signUp(@ModelAttribute("form") @Valid SignUpForm form, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.put("form", form);
            return "sign_up";
        }
        try {
            signUpService.signUp(form);
        } catch (DuplicateEmailException ex) {
            map.put("form", form);
            result.rejectValue("email", "error.duplicate.email");
            return "sign_up";
        }
        return "redirect:/signIn";
    }

    @GetMapping("/signIn")
    public String getSignInPage(Authentication authentication, ModelMap map, HttpServletRequest request) {
        if (authentication != null) {
            return "redirect:articles";
        }
        map.put("error", request.getParameterMap().containsKey("error"));
        map.put("form", new SignInForm());
        return "sign_in";
    }

    @GetMapping("/forbidden")
    public String getForbiddenPage(ModelMap map) {
        map.put("status", HttpStatus.FORBIDDEN.value());
        map.put("message", "Access to this resource on the server is denied!");
        return "error";
    }

}
