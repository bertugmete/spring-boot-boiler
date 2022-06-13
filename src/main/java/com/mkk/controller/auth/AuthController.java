package com.mkk.controller.auth;

import com.mkk.dto.auth.LoginDto;
import com.mkk.dto.auth.RegisterDto;
import com.mkk.mapper.auth.AuthMapper;
import com.mkk.mapper.user.UserMapper;
import com.mkk.request.auth.LoginRequest;
import com.mkk.request.auth.RefreshTokenRequest;
import com.mkk.request.auth.RegisterRequest;
import com.mkk.response.login.LoginResponse;
import com.mkk.response.register.RegisterResponse;
import com.mkk.security.JwtTokenProvider;
import com.mkk.service.auth.AuthService;
import com.mkk.service.refreshtoken.RefreshTokenService;
import com.mkk.service.user.UserService;
import com.mkk.util.web.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    private final AuthMapper authMapper;

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        LoginDto loginDto = authService.login(authMapper.loginRequestToLoginDto(loginRequest));
        return ResponseEntity.ok(new BaseResponse<>(authMapper.loginResponseToLoginDto(loginDto)));
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) {
        RegisterDto registerDto = authService.register(authMapper.registerRequestToRegisterDto(registerRequest));
        return ResponseEntity.ok(new BaseResponse<>(authMapper.registerDtoToRegisterResponse(registerDto)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<RegisterResponse>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RegisterDto registerDto = authService.refreshAccessToken(authMapper.refreshTokenRequestToRefreshTokenDto(refreshTokenRequest));
        return ResponseEntity.ok(new BaseResponse<>(userMapper.registerDtoToRegisterResponse(registerDto)));
    }

}
