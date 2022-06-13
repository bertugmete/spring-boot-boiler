package com.mkk.request.auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
    private Integer userId;
}
