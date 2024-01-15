package com.Test.exception;

import com.Test.payload.ErrorDtls;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDtls> catchException(
            ResourceNotFound exception,
            WebRequest webRequest
    ){
        ErrorDtls errorDtls = new ErrorDtls(new Date(), exception.getMessage(), webRequest.getDescription(true));
        return new ResponseEntity<>(errorDtls, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
