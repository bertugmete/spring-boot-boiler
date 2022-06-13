package com.mkk.mapper.user;

import com.mkk.dto.refreshtoken.RefreshTokenDto;
import com.mkk.entity.refreshtoken.RefreshToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshTokenDto refreshTokenToRefreshTokenDto(RefreshToken entity);
}
