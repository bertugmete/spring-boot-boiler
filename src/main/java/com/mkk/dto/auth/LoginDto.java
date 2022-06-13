package com.mkk.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginDto extends AuthDto {
    private Integer userId;
    private String username;
    private String accessToken;
    private String refreshToken;
    private String password;
}
