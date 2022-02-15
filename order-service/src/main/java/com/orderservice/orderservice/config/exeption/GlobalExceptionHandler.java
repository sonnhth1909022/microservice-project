package com.orderservice.orderservice.config.exeption;


import com.orderservice.orderservice.ulti.RESTResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    //handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request)
    {
        HashMap<String, Object> restResponse =
                new RESTResponse.Error().setMessage(exception.getMessage()).setStatus(HttpStatus.NOT_FOUND.value()).setDetail(request.getDescription(false)).build();
        return new ResponseEntity(restResponse, HttpStatus.NOT_FOUND);
    }

    //handle specific exceptions
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ResourceNotFoundException exception, WebRequest request)
    {
        HashMap<String, Object> restResponse =
                new RESTResponse.Error().setMessage(exception.getMessage()).setStatus(HttpStatus.NOT_FOUND.value()).setDetail(request.getDescription(false)).build();
        return new ResponseEntity(restResponse, HttpStatus.NOT_FOUND);
    }


    //handle global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleresourceNotFoundException(ResourceNotFoundException exception, WebRequest request)
    {
        HashMap<String, Object> restResponse =
                new RESTResponse.Error().setMessage(exception.getMessage()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).setDetail(request.getDescription(false)).build();
        return new ResponseEntity(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //handling custom validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException exception)
    {
        HashMap<String, Object> restResponse =
                new RESTResponse.Error().setMessage("Validation Error").setStatus(HttpStatus.BAD_REQUEST.value())
                .setDetail(exception.getBindingResult().getFieldError().getDefaultMessage()).build();
        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    //handling RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex)
    {
        HashMap<String, Object> restResponse =
                new RESTResponse.Error().setStatus(HttpStatus.BAD_REQUEST.value()).setMessage(ex.getLocalizedMessage()).build();
        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
}
