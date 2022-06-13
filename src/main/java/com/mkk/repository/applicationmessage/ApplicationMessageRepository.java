package com.mkk.repository.applicationmessage;

import com.mkk.entity.applicationmessage.ApplicationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationMessageRepository extends JpaRepository<ApplicationMessage, Integer> {
    Optional<ApplicationMessage> findByName(String name);
}
