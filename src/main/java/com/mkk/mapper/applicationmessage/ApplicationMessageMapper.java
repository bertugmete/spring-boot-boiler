package com.mkk.mapper.applicationmessage;

import com.mkk.dto.applicationmessage.ApplicationMessageDto;
import com.mkk.entity.applicationmessage.ApplicationMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationMessageMapper {
    ApplicationMessageDto applicationMessageToApplicationMessageDto(ApplicationMessage applicationMessage);
}
