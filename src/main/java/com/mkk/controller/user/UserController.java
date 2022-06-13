package com.mkk.controller.user;

import com.mkk.annotation.MKKValidator;
import com.mkk.dto.user.PaginatedMkkUserDto;
import com.mkk.mapper.user.UserMapper;
import com.mkk.request.user.UserRequest;
import com.mkk.response.user.UserResponse;
import com.mkk.service.user.UserService;
import com.mkk.util.validation.UserControllerValidator;
import com.mkk.util.web.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<BaseResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> userResponseList = userMapper.userDtoListToUserResponseList(userService.getAllUsers());
        return ResponseEntity.ok(new BaseResponse<>(userResponseList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(new BaseResponse<>(userMapper.userDtoToUserResponse(userService.getUserById(id))));
    }

    @PostMapping
    @MKKValidator(validator = UserControllerValidator.CreateUserValidator.class)
    public ResponseEntity<BaseResponse<UserResponse>> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(new BaseResponse<>(userMapper.userDtoToUserResponse(
                userService.createUser(userMapper.userRequestToUserDto(request)))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(@RequestBody UserRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(new BaseResponse<>(userMapper.userDtoToUserResponse(
                userService.updateUser(id, userMapper.userRequestToUserDto(request)))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(new BaseResponse<>());
    }

    @PostMapping("/search")
    public ResponseEntity<BaseResponse<PageImpl<UserResponse>>> searchUser(@RequestBody UserRequest request, @RequestParam Integer size, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        PaginatedMkkUserDto mkkUserDto = userService.searchUser(userMapper.userRequestToUserDto(request), pageable);

        List<UserResponse> notificationResponseList = mkkUserDto.getMkkUserList().stream()
                .map(userMapper::userDtoToUserResponse).collect(Collectors.toList());

        return ResponseEntity.ok(new BaseResponse<>(new PageImpl<>(notificationResponseList, mkkUserDto.getPageable(), mkkUserDto.getCount())));
    }
}
