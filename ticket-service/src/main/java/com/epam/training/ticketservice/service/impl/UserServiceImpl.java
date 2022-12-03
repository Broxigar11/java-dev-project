package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.entity.UserEntity;
import com.epam.training.ticketservice.model.UserDto;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserDto loggedInUser = null;

    @Override
    public boolean signInPrivileged(String username, String password) {
        final Optional<UserEntity> user = userRepository
                .findByUsernameAndPasswordAndRole(username, password, UserEntity.Role.ADMIN);
        if (user.isEmpty()) {
            return false;
        }
        loggedInUser = new UserDto(user.get().getUsername(), user.get().getRole());
        return true;
    }

    @Override
    public boolean signOut() {
        if (Objects.isNull(loggedInUser)) {
            return false;
        }
        loggedInUser = null;
        return true;
    }

    @Override
    public Optional<UserDto> describe() {
        return Optional.ofNullable(loggedInUser);
    }
}
