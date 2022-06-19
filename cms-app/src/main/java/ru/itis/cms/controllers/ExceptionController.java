package ru.itis.cms.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.cms.exceptions.EntityNotFoundException;
import ru.itis.cms.exceptions.NotHavePermissionsException;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFound() {
        return createModelAndView(HttpStatus.NOT_FOUND.value(), "Page not found");
    }

    @ExceptionHandler(NotHavePermissionsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView forbidden(Exception exception) {
        return createModelAndView(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    private ModelAndView createModelAndView(int status, String message) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("status", status);
        mav.addObject("message", message);
        mav.setViewName("error");
        return mav;
    }
}
