package com.example.qnaboard.advice;

import com.example.qnaboard.exception.CustomException;
import com.example.qnaboard.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(CustomException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }
}
