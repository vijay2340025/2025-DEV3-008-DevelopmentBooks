package com.kata.DevelopmentBooks.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    private int status;
    private String error;
    private List<String> message;
    private LocalDateTime timestamp;

    public ApiError(int status, String error, List<String> message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
