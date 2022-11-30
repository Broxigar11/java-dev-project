package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    void createRoom(RoomDto roomDto);

    void updateRoom(String name, RoomDto roomDto);

    void deleteRoom(String name);

    Optional<RoomDto> findRoomByName(String name);

    List<RoomDto> getRoomList();

}
