package com.test.ecomdemo.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.test.ecomdemo.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import com.test.ecomdemo.model.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CheckoutAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(Exception exception) {
        log.error("Checkout Error: {}", exception.getMessage());
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler({JsonParseException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleParseException(Exception exception) {
        log.error("Checkout Error: {}", exception);
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Request is invalid, kindly check and try again");
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("Checkout Error: {}", exception.getMessage());
        var errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong, please try again later");
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }
}
