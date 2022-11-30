package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> signInPrivileged(String username, String password);

    Optional<UserDto> signOut();

    Optional<UserDto> describe();

}
