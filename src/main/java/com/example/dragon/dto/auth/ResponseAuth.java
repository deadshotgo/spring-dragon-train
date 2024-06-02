package com.example.dragon.dto.auth;


import com.example.dragon.dto.user.ResponseUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAuth {
    private String token;
    private ResponseUser responseUser;

    public ResponseAuth(String token) {
        this.token = token;
    }

    public ResponseAuth(String token, ResponseUser user) {
        this.token = token;
        this.responseUser = user;
    }
}
