package br.com.alura.moneymanagementapi.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionDto {

    private final LocalDateTime timeStamp;
    private final HttpStatus httpStatus;
    private final String message;

    public ExceptionDto(HttpStatus status, String message) {
        this.timeStamp = LocalDateTime.now();
        this.httpStatus = status;
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
