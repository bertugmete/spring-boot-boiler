package com.mkk.dto.user;

import com.mkk.dto.pagination.PaginatedDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class PaginatedMkkUserDto extends PaginatedDto {
    List<MkkUserDto> mkkUserList;
}
