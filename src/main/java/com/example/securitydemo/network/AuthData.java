package com.example.securitydemo.network;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthData {
    private String uid;

    public static BaseResponseData success(String uid) {
        return BaseResponseData.success(new AuthData(uid));
    }
}
