package com.mkk.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MkkUserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
