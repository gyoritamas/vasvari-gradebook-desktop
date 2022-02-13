package org.vasvari.gradebook.controllers.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@Slf4j
public class HttpClientErrorAdvice {
    @ResponseBody
    @ExceptionHandler(HttpClientErrorException.class)
    String handleException(HttpClientErrorException ex){
        log.warn(ex.getMessage());

        return ex.getMessage();
    }
}
