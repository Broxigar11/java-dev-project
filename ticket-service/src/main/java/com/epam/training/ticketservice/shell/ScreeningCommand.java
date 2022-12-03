package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.entity.UserEntity;
import com.epam.training.ticketservice.model.ScreeningDto;
import com.epam.training.ticketservice.model.UserDto;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommand {

    private final UserService userService;

    private final ScreeningService screeningService;

    @ShellMethod(key = "create screening", value = "creates a screening")
    @ShellMethodAvailability("isAdmin")
    public String createScreening(String movieTitle, String roomName, String screeningTime) {
        return screeningService.createScreening(new ScreeningDto(movieTitle, roomName, screeningTime));
    }

    @ShellMethod(key = "delete screening", value = "deletes a screening")
    @ShellMethodAvailability("isAdmin")
    public String deleteScreening(String movieTitle, String roomName, String screeningTime) {
        return screeningService.deleteScreening(new ScreeningDto(movieTitle, roomName, screeningTime));
    }

    @ShellMethod(key = "list screenings", value = "list all movies")
    public String listScreenings() {
        return screeningService.getScreeningList();
    }

    public Availability isAdmin() {
        Optional<UserDto> userDto = userService.describe();

        return userDto.isPresent() && userDto.get().getRole() == UserEntity.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
