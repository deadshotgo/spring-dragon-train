package com.example.dragon.dto.error;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorValidateResponse {
    private Map<String, String> errors;

    public ErrorValidateResponse(Map<String, String> errors) {
        this.errors = errors;
    }
}