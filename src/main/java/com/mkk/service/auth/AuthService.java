package com.mkk.service.auth;

import com.mkk.dto.auth.AuthDto;
import com.mkk.dto.auth.LoginDto;
import com.mkk.dto.auth.RegisterDto;
import com.mkk.dto.refreshtoken.RefreshTokenDto;
import com.mkk.dto.user.MkkUserDto;
import com.mkk.entity.user.MkkUser;
import com.mkk.exception.auth.InvalidRefreshToken;
import com.mkk.mapper.auth.AuthMapper;
import com.mkk.mapper.user.UserMapper;
import com.mkk.response.login.LoginResponse;
import com.mkk.response.register.RegisterResponse;
import com.mkk.security.JwtTokenProvider;
import com.mkk.service.refreshtoken.RefreshTokenService;
import com.mkk.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    private final AuthMapper authMapper;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final String BEARER = "Bearer ";

    public LoginDto login(LoginDto loginDto) {
        String jwtToken = authenticateUserAndGetToken(loginDto);
        MkkUserDto mkkUserDto = userService.getUserByUserName(loginDto.getUsername());
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(BEARER + jwtToken)
                .refreshToken(refreshTokenService.createRefreshToken(mkkUserDto))
                .username(mkkUserDto.getUsername())
                .userId(mkkUserDto.getId())
                .build();

        return authMapper.loginResponseToLoginDto(loginResponse);
    }

    public RegisterDto register(RegisterDto registerDto) {
        MkkUserDto mkkUserDto = userService.getUserByUserName(registerDto.getUsername());
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userService.createUser(userMapper.registerRequestToMkkUserDto(registerDto));
        String jwtToken = authenticateUserAndGetToken(registerDto);
        RegisterResponse registerResponse = RegisterResponse.builder()
                .accessToken(BEARER + jwtToken)
                .refreshToken(refreshTokenService.createRefreshToken(mkkUserDto))
                .userId(mkkUserDto.getId())
                .build();

        return authMapper.registerResponseToRegisterDto(registerResponse);
    }

    public RegisterDto refreshAccessToken(RefreshTokenDto refreshTokenDto) {
        RefreshTokenDto refreshTokenDtoFromDb = refreshTokenService.getByUserId(refreshTokenDto.getUser().getId());

        if (refreshTokenDtoFromDb == null) {
            throw new InvalidRefreshToken();
        }

        if (Objects.equals(refreshTokenDtoFromDb.getToken(), refreshTokenDto.getRefreshToken()) &&
                !refreshTokenService.isRefreshTokenExpired(refreshTokenDtoFromDb)) {

            MkkUser mkkUser = refreshTokenDtoFromDb.getUser();
            String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(mkkUser.getId());
            RegisterResponse response = RegisterResponse.builder()
                    .accessToken(BEARER + jwtToken)
                    .userId(mkkUser.getId())
                    .refreshToken(refreshTokenDtoFromDb.getRefreshToken())
                    .build();
            return authMapper.registerResponseToRegisterDto(response);
        } else {
            throw new InvalidRefreshToken();
        }
    }

    private <R extends AuthDto> String authenticateUserAndGetToken(R auth) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                auth.getUsername(), auth.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateJwtToken(authentication);
    }
}
