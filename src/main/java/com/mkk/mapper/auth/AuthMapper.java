package com.mkk.mapper.auth;

import com.mkk.dto.auth.LoginDto;
import com.mkk.dto.auth.RegisterDto;
import com.mkk.dto.refreshtoken.RefreshTokenDto;
import com.mkk.request.auth.LoginRequest;
import com.mkk.request.auth.RefreshTokenRequest;
import com.mkk.request.auth.RegisterRequest;
import com.mkk.response.login.LoginResponse;
import com.mkk.response.register.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    LoginDto loginRequestToLoginDto(LoginRequest loginRequest);
    LoginDto loginResponseToLoginDto(LoginResponse loginResponse);
    LoginResponse loginResponseToLoginDto(LoginDto loginDto);
    RegisterDto registerRequestToRegisterDto(RegisterRequest loginRequest);

    RegisterDto registerResponseToRegisterDto(RegisterResponse registerResponse);
    RegisterResponse registerDtoToRegisterResponse(RegisterDto registerResponse);

    @Mapping(source = "userId", target = "user.id")
    RefreshTokenDto refreshTokenRequestToRefreshTokenDto(RefreshTokenRequest refreshTokenRequest);
}
