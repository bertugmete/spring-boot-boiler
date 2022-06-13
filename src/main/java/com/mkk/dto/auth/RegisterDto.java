package com.mkk.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RegisterDto extends AuthDto {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
    private String firstName;
    private String lastName;
}
