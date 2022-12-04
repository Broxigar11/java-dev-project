package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entity.RoomEntity;
import com.epam.training.ticketservice.model.RoomDto;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl testRoomService;

    private final RoomEntity TEST_ROOM_ENTITY = new RoomEntity("Room1", 12, 12);

    private final RoomDto TEST_ROOM_DTO = new RoomDto("Room1", 12, 12);

    @Test
    void testCreateMovieShouldStoreGivenMovieWhenInputIsValid() {
        // Given
        Mockito.when(roomRepository
                .save(TEST_ROOM_ENTITY)).thenReturn(TEST_ROOM_ENTITY);

        // When
        testRoomService.createRoom(TEST_ROOM_DTO);

        // Then
        Mockito.verify(roomRepository
        ).save(TEST_ROOM_ENTITY);
    }

    @Test
    void testUpdateMovieShouldUpdateGivenMovieWhenInputIsValid() {
        // Given
        Mockito.when(roomRepository
                        .findByName(TEST_ROOM_ENTITY.getName()))
                .thenReturn(Optional.of(TEST_ROOM_ENTITY));

        // When
        testRoomService.updateRoom(TEST_ROOM_DTO);

        // Then
        Mockito.verify(roomRepository
        ).findByName(TEST_ROOM_ENTITY.getName());
    }

    @Test
    void testDeleteMovieShouldBeDeletedGivenMovieWhenInputIsValid() {
        // Given
        Mockito.when(roomRepository
                        .findByName(TEST_ROOM_ENTITY.getName()))
                .thenReturn(Optional.of(TEST_ROOM_ENTITY));

        // When
        testRoomService.deleteRoom(TEST_ROOM_DTO.getName());

        // Then
        Mockito.verify(roomRepository
        ).findByName(TEST_ROOM_ENTITY.getName());
    }

    @Test
    void testGetMovieListShouldReturnListWithOneElement() {
        //Given
        Mockito.when(roomRepository
                .findAll()).thenReturn(List.of(TEST_ROOM_ENTITY));
        List<RoomDto> expected = List.of(TEST_ROOM_DTO);

        //When
        List<RoomDto> actual = testRoomService.getRoomList();

        //Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(roomRepository
        ).findAll();
    }

}
