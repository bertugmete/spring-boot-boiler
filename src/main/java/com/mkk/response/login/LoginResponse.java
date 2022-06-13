package com.mkk.response.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Integer userId;
    private String username;
    private String accessToken;
    private String refreshToken;
}
