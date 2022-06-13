package com.mkk.response.register;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
}
