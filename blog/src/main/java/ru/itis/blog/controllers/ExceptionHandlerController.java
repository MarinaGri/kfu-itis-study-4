package ru.itis.blog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.blog.dto.response.ExceptionResponse;
import ru.itis.blog.validation.http.ValidationErrorDto;
import ru.itis.blog.validation.http.ValidationExceptionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach((error)
                -> errors.add(error.getDefaultMessage()));

        return new ResponseEntity<>(new ExceptionResponse(errors), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleExceptions(Throwable throwable) {
        return new ResponseEntity<>(
                new ExceptionResponse(Collections.singletonList(throwable.getMessage())),
                HttpStatus.BAD_REQUEST
        );
    }
}
