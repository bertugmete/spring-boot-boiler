package com.mkk.dto.applicationmessage;

import com.mkk.enums.ApplicationMessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationMessageDto {
    private Integer id;
    private String code;
    private String name;
    private String message;
    private ApplicationMessageTypeEnum type;

}
