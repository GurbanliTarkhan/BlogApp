package com.tarkhan.blogapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogApiException extends RuntimeException{
    public BlogApiException(String message) {
        super(String.format(message));
    }
}
