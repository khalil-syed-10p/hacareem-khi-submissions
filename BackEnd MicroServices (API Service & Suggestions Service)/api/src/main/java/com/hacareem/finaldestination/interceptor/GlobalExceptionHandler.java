package com.hacareem.finaldestination.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by waqas on 4/30/17.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void UncaughtException(final Exception ex){
        System.out.println(ex.getMessage());

    }
}
