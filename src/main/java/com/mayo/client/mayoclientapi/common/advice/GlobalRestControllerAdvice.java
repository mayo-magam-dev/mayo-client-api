package com.mayo.client.mayoclientapi.common.advice;

import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorStatus> handleException(ApplicationException e) {

        ErrorStatus errorStatus = e.getErrorStatus();

        return new ResponseEntity<>(errorStatus, errorStatus.toHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorStatus> handleException(Exception e) {

        ErrorStatus errorStatus = ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500, LocalDateTime.now());

        return new ResponseEntity<>(errorStatus, errorStatus.toHttpStatus());
    }
}

