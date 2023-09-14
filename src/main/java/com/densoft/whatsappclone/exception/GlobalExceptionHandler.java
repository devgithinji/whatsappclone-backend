package com.densoft.whatsappclone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException e, WebRequest webRequest) {

        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), webRequest.getDescription(false), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetail> messageExceptionHandler(MessageException e, WebRequest webRequest) {

        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), webRequest.getDescription(false), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetail> chatExceptionHandler(ChatException e, WebRequest webRequest) {

        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), webRequest.getDescription(false), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> messageExceptionHandler(MethodArgumentNotValidException e, WebRequest webRequest) {

        ErrorDetail errorDetail = new ErrorDetail("Validation error", webRequest.getDescription(false), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetail> messageExceptionHandler(NoHandlerFoundException e, WebRequest webRequest) {

        ErrorDetail errorDetail = new ErrorDetail("Endpoint not found", webRequest.getDescription(false), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest webRequest) {

        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), webRequest.getDescription(false), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
    }
}
