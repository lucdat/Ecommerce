package com.ecommerce.handlerException;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UniqueConstrainException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String,String> handlerResourceNotFoundException(ResourceNotFoundException ex){
        Map<String,String> errors = new HashMap<>();
        errors.put("message:",ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UniqueConstrainException.class)
    public Map<String,String> handlerUniqueException(UniqueConstrainException ex){
        Map<String,String> errors = new HashMap<>();
        String[] message = ex.getMessage().split(":");
        errors.put(message[0], message[1]);
        return errors;
    }

}
