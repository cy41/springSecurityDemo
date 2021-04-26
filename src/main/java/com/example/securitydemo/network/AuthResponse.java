package com.example.securitydemo.network;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Integer code;
    private String message;
    private String uid;

    public static AuthResponse success(String uid) {
        return new AuthResponse(200, "success", uid);
    }
}
