package com.mkk.service.user;

import com.mkk.dto.user.MkkUserDto;
import com.mkk.security.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MkkUserDto mkkUserDto  = userService.getUserByUserName(username);
        return JwtUserDetails.create(mkkUserDto);
    }

    public UserDetails loadUserById(Integer id) throws UsernameNotFoundException {
        MkkUserDto mkkUserDto  = userService.getUserById(id);
        return JwtUserDetails.create(mkkUserDto);
    }
}
