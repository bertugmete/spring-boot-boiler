package com.mkk.mapper.user;

import com.mkk.dto.auth.RegisterDto;
import com.mkk.dto.user.MkkUserDto;
import com.mkk.entity.user.MkkUser;
import com.mkk.request.user.UserRequest;
import com.mkk.response.register.RegisterResponse;
import com.mkk.response.user.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<MkkUserDto> userListToUserDtoList(List<MkkUser> mkkUserList);
    List<UserResponse> userDtoListToUserResponseList(List<MkkUserDto> mkkUserDtoList);
    MkkUserDto userToUserDto(MkkUser mkkUser);
    UserResponse userDtoToUserResponse(MkkUserDto mkkUserDto);

    MkkUserDto userRequestToUserDto(UserRequest userRequest);
    MkkUser userDtoToUser(MkkUserDto mkkUserDto);
    MkkUserDto registerRequestToMkkUserDto(RegisterDto registerDto);

    RegisterResponse registerDtoToRegisterResponse(RegisterDto registerDto);
}
