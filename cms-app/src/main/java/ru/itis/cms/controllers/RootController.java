package ru.itis.cms.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.cms.aspects.Loggable;

@Controller
public class RootController {

    @Loggable
    @GetMapping("/")
    public String getDefault(Authentication authentication) {
        return authentication == null ? "redirect:/signIn" : "redirect:/articles";
    }

}
