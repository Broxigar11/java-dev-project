package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.entity.UserEntity;
import com.epam.training.ticketservice.model.UserDto;
import com.epam.training.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class UserCommand {

    public final UserService userService;

    @ShellMethod(key = "sign in privileged", value = "sign in with admin account")
    public String signInPrivileged(String username, String password) {

        return userService.signInPrivileged(username, password)
                ? "Login successful"
                : "Login failed due to incorrect credentials";
    }

    @ShellMethod(key = "sign out", value = "sign out")
    public String signOut() {
        userService.signOut();

        return "Signed out";
    }

    @ShellMethod(key = "describe account", value = "get information about the signed in account")
    public String describeAccount() {
        Optional<UserDto> user = userService.describe();
        if (user.isEmpty()) {
            return "You are not signed in";
        }

        UserDto userDto = user.get();
        return userDto.getRole() == UserEntity.Role.ADMIN
                ? "Signed in with privileged account " + userDto.getUsername()
                : "Signed in with account " + userDto.getUsername();
    }

}
