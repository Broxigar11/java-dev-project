package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entity.MovieEntity;
import com.epam.training.ticketservice.entity.RoomEntity;
import com.epam.training.ticketservice.entity.ScreeningEntity;
import com.epam.training.ticketservice.model.MovieDto;
import com.epam.training.ticketservice.model.ScreeningDto;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.impl.ScreeningServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ScreeningServiceImplTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private ScreeningServiceImpl testScreeningService;

    private final MovieEntity TEST_MOVIE_ENTITY = new MovieEntity("Lord of the Rings", "fantasy", 178);

    private final RoomEntity TEST_ROOM_ENTITY = new RoomEntity("Room1", 12, 12);

    private final ScreeningDto TEST_SCREENING_DTO = new ScreeningDto(
            "Lord of the Rings", "Room1", "2020-12-13 13:00"
    );

    @Test
    void testCreateScreeningShouldReturnSuccessMessageAndCreateScreeningAndThereAreNoOverlaps() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date screeningTime = formatter.parse(TEST_SCREENING_DTO.getScreeningTime());
        final ScreeningEntity TEST_SCREENING_ENTITY = new ScreeningEntity(
                TEST_MOVIE_ENTITY,
                TEST_ROOM_ENTITY,
                screeningTime
        );

        String expected = "Screening Lord of the Rings (fantasy, 178 minutes), screened in room Room1,"
                + " at 2020-12-13 13:00 created";

        Mockito.when(movieRepository.findByTitle(TEST_MOVIE_ENTITY.getTitle()))
                .thenReturn(Optional.of(TEST_MOVIE_ENTITY));

        Mockito.when(roomRepository.findByName(TEST_ROOM_ENTITY.getName()))
                .thenReturn(Optional.of(TEST_ROOM_ENTITY));

        Mockito.when(screeningRepository.findAllByRoom(TEST_ROOM_ENTITY))
                .thenReturn(new ArrayList<>());

        Mockito.when(screeningRepository.save(TEST_SCREENING_ENTITY)).thenReturn(TEST_SCREENING_ENTITY);

        //When
        String actual = testScreeningService.createScreening(TEST_SCREENING_DTO);

        //Then
        Assertions.assertEquals(expected, actual);

        Mockito.verify(movieRepository).findByTitle(TEST_MOVIE_ENTITY.getTitle());
        Mockito.verify(roomRepository).findByName(TEST_ROOM_ENTITY.getName());
        Mockito.verify(screeningRepository).findAllByRoom(TEST_ROOM_ENTITY);
        Mockito.verify(screeningRepository).save(TEST_SCREENING_ENTITY);

    }

    @Test
    void testGetScreeningListShouldReturnStringWithOneLine() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date screeningTime = formatter.parse(TEST_SCREENING_DTO.getScreeningTime());
        final ScreeningEntity TEST_SCREENING_ENTITY = new ScreeningEntity(
                TEST_MOVIE_ENTITY,
                TEST_ROOM_ENTITY,
                screeningTime
        );
        String expected = "Lord of the Rings (fantasy, 178 minutes), screened in room Room1, at 2020-12-13 13:00";
        Mockito.when(screeningRepository.findAll()).thenReturn(List.of(TEST_SCREENING_ENTITY));

        //When
        String actual = testScreeningService.getScreeningList();

        //Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(screeningRepository).findAll();
    }

}
