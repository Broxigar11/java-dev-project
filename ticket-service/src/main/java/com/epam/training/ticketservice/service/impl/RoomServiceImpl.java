package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.entity.MovieEntity;
import com.epam.training.ticketservice.entity.RoomEntity;
import com.epam.training.ticketservice.model.MovieDto;
import com.epam.training.ticketservice.model.RoomDto;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public void createRoom(RoomDto roomDto) {
        roomRepository.save(new RoomEntity(
                roomDto.getName(),
                roomDto.getRows(),
                roomDto.getColumns()
        ));
    }

    @Override
    public void updateRoom(String name, RoomDto roomDto) {
        RoomEntity room = roomRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("There's no room by this name!"));

        roomRepository.updateRoomEntity(
                roomDto.getName(),
                roomDto.getRows(),
                roomDto.getColumns()
        );
    }

    @Override
    public void deleteRoom(String name) {
        RoomEntity room = roomRepository.findByName(name).
                orElseThrow(() -> new IllegalArgumentException("There's no movie by this title!"));

        roomRepository.delete(room);
    }

    @Override
    public Optional<RoomDto> findRoomByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<RoomDto> getRoomList() {
        List<RoomEntity> roomList = roomRepository.findAll();

        return roomEntityListToRoomDtoList(roomList);
    }

    private List<RoomDto> roomEntityListToRoomDtoList(List<RoomEntity> roomEntityList) {
        List<RoomDto> roomDtoList = new ArrayList<>();

        for (RoomEntity entity : roomEntityList) {
            roomDtoList.add(new RoomDto(
                    entity.getName(),
                    entity.getRows(),
                    entity.getColumns()
            ));
        }
        return roomDtoList;
    }
}
