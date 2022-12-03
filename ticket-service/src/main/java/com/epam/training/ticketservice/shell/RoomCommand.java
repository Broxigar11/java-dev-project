package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.entity.UserEntity;
import com.epam.training.ticketservice.model.RoomDto;
import com.epam.training.ticketservice.model.UserDto;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommand {

    private final UserService userService;

    private final RoomService roomService;

    @ShellMethod(key = "create room", value = "creates a room")
    @ShellMethodAvailability("isAdmin")
    public String createRoom(String name, Integer rows, Integer columns) {
        roomService.createRoom(new RoomDto(name, rows, columns));

        return "Room " + name + " created";
    }

    @ShellMethod(key = "update room", value = "updates a room")
    @ShellMethodAvailability("isAdmin")
    public String updateRoom(String name, Integer rows, Integer columns) {
        RoomDto roomDto = new RoomDto(name, rows, columns);
        roomService.updateRoom(name, roomDto);
        return "Room " + name + " updated";
    }

    @ShellMethod(key = "delete room", value = "deletes a room")
    @ShellMethodAvailability("isAdmin")
    public String deleteRoom(String name) {
        roomService.deleteRoom(name);
        return "Room " + name + " deleted";
    }

    @ShellMethod(key = "list rooms", value = "list all room")
    public String listRoom() {
        List<RoomDto> roomList = roomService.getRoomList();
        if (roomList.isEmpty()) {
            return "There are no rooms at the moment";
        }
        String response = "";
        for (int i = 0; i < roomList.size(); i++) {
            RoomDto room = roomList.get(i);
            response += room.getName()
                    + " with " + room.getRows() * roomList.get(i).getColumns()
                    + " seats, " + room.getRows()
                    + " rows, and " + room.getColumns()
                    + " columns";
            if (i < roomList.size() - 1) {
                response += "\n";
            }
        }

        return response;
    }

    public Availability isAdmin() {
        Optional<UserDto> userDto = userService.describe();

        return userDto.isPresent() && userDto.get().getRole() == UserEntity.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }

}
