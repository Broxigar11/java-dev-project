package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommand {

    private final UserService userService;

    private final RoomService roomService;

}
