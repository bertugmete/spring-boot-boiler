package com.mkk.service.user;

import com.mkk.dataaccess.common.specification.MkkUserSpec;
import com.mkk.dto.user.MkkUserDto;
import com.mkk.dto.user.PaginatedMkkUserDto;
import com.mkk.entity.user.MkkUser;
import com.mkk.exception.user.UserNotFoundException;
import com.mkk.mapper.user.UserMapper;
import com.mkk.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<MkkUserDto> getAllUsers() {
        return userMapper.userListToUserDtoList(userRepository.findAll());
    }

    public MkkUserDto getUserById(Integer id) {
        return userMapper.userToUserDto(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public MkkUserDto getUserByUserName(String userName) {
        return userMapper.userToUserDto(userRepository.findByUsername(userName));
    }

    public MkkUserDto createUser(MkkUserDto mkkUserDto) {
        return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(mkkUserDto)));
    }

    public MkkUserDto updateUser(Integer id, MkkUserDto mkkUserDto) {
        mkkUserDto.setId(id);
        MkkUser mkkUser = userMapper.userDtoToUser(mkkUserDto);
        return userMapper.userToUserDto(userRepository.save(mkkUser));
    }

    public void deleteUserById(Integer id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public PaginatedMkkUserDto searchUser(MkkUserDto mkkUserDto, Pageable pageable) {
        MkkUserSpec mkkUserSpec = new MkkUserSpec();
        mkkUserSpec.setFirstName(mkkUserDto.getFirstName());
        mkkUserSpec.setLastName(mkkUserDto.getLastName());


        Page<MkkUser> mkkUsers = userRepository.findAll(mkkUserSpec, pageable);

        List<MkkUserDto> notificationDtoList = mkkUsers.getContent().stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());

        return PaginatedMkkUserDto.builder()
                .mkkUserList(notificationDtoList)
                .count(mkkUsers.getTotalElements())
                .page(mkkUsers.getPageable().getPageNumber())
                .size(mkkUsers.getPageable().getPageSize())
                .pageable(pageable)
                .totalPageNumber(mkkUsers.getTotalPages())
                .build();
    }
}
