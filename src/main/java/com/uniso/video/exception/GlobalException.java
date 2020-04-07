package com.uniso.video.exception;

import com.uniso.video.domain.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> globalExceptionHandler(){
        ApiResponse<String> apiResponse = new ApiResponse<>();

        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponse.setErrorCode(-1);
        apiResponse.setErrorMessage("Something went wrong!");

        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
