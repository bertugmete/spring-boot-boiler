package com.mkk.service.refreshtoken;

import com.mkk.dto.refreshtoken.RefreshTokenDto;
import com.mkk.dto.user.MkkUserDto;
import com.mkk.entity.refreshtoken.RefreshToken;
import com.mkk.mapper.user.RefreshTokenMapper;
import com.mkk.mapper.user.UserMapper;
import com.mkk.repository.refreshtoken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${security.jwt.token.refresh-token-expire-length}")
    private Long expirationTime;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;

    private final RefreshTokenMapper refreshTokenMapper;

    public boolean isRefreshTokenExpired(RefreshTokenDto refreshTokenDto) {
        return refreshTokenDto.getExpireDate().before(new Date());
    }

    public String createRefreshToken(MkkUserDto mkkUserDto) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(mkkUserDto.getId());
        if(refreshToken == null) {
            refreshToken =	new RefreshToken();
            refreshToken.setUser(userMapper.userDtoToUser(mkkUserDto));
        }

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(Date.from(Instant.now().plusSeconds(expirationTime)));

        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public RefreshTokenDto getByUserId(Integer userId) {
        return refreshTokenMapper.refreshTokenToRefreshTokenDto(refreshTokenRepository.findByUserId(userId));
    }
}
