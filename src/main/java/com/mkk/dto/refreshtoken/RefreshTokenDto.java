package com.mkk.dto.refreshtoken;

import com.mkk.entity.user.MkkUser;
import lombok.Data;

import java.util.Date;

@Data
public class RefreshTokenDto {
    private Integer id;
    private MkkUser user;
    private String token;
    private Date expireDate;
    private String refreshToken;
}
