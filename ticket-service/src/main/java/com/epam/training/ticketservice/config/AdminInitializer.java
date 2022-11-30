package com.epam.training.ticketservice.config;

import com.epam.training.ticketservice.entity.UserEntity;
import com.epam.training.ticketservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.findAll().isEmpty()) {
            UserEntity admin = new UserEntity(null, "admin", UserEntity.Role.ADMIN);
        }
    }
}
