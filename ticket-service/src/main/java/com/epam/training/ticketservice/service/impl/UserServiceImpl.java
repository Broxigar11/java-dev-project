package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.entity.UserEntity;
import com.epam.training.ticketservice.model.UserDto;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserDto loggedInUser = null;

    @Override
    public Optional<UserDto> signInPrivileged(String username, String password) {
        final Optional<UserEntity> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        loggedInUser = new UserDto(user.get().getUsername(), user.get().getRole());
        return describe();
    }

    @Override
    public Optional<UserDto> signOut() {
        final Optional<UserDto> previouslyLoggedInUser = describe();
        loggedInUser = null;
        return previouslyLoggedInUser;    }

    @Override
    public Optional<UserDto> describe() {
        return Optional.ofNullable(loggedInUser);    }
}
