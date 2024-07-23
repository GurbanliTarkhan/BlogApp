package com.tarkhan.blogapp.exception;

import com.tarkhan.blogapp.model.ErrorResponseModel;
import com.tarkhan.blogapp.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorResponseModel> handleBlogApiException(
            BlogApiException ex,
            WebRequest request) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseModel> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest request
    ) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseModel
                                .builder()
                                .statusMsg(ex.getMessage())
                                .build()
                );
    }
}
