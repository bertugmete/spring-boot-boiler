package com.mkk.service.applicationmessage;

import com.mkk.dto.applicationmessage.ApplicationMessageDto;
import com.mkk.entity.applicationmessage.ApplicationMessage;
import com.mkk.exception.ResourceNotFoundException;
import com.mkk.mapper.applicationmessage.ApplicationMessageMapper;
import com.mkk.repository.applicationmessage.ApplicationMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationMessageService {

    private final ApplicationMessageRepository applicationMessageRepository;
    private final ApplicationMessageMapper applicationMessageMapper;

    public ApplicationMessageDto findApplicationMessageByName(String name) {
        List<ApplicationMessage> applicationMessageList = applicationMessageRepository.findAll();
        ApplicationMessage applicationMessage = applicationMessageRepository.findByName(name).orElseThrow(ResourceNotFoundException::new);

        return applicationMessageMapper.applicationMessageToApplicationMessageDto(applicationMessage);
    }
}
