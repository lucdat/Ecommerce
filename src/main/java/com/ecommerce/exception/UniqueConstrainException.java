package com.ecommerce.exception;

public class UniqueConstrainException extends RuntimeException{
    public UniqueConstrainException(String message){
        super(message);
    }
}
