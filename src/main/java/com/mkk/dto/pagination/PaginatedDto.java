package com.mkk.dto.pagination;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;

@Data
@SuperBuilder
public class PaginatedDto {
    private long count;
    private int page;
    private int size;
    private Pageable pageable;
    private int totalPageNumber;
}
