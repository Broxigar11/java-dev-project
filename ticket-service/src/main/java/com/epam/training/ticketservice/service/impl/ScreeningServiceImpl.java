package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.entity.MovieEntity;
import com.epam.training.ticketservice.entity.RoomEntity;
import com.epam.training.ticketservice.entity.ScreeningEntity;
import com.epam.training.ticketservice.model.MovieDto;
import com.epam.training.ticketservice.model.ScreeningDto;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    @Override
    public void createScreening(ScreeningDto screeningDto) {
        screeningRepository.save(screeningDtoToScreeningEntity(screeningDto));
    }

    @Override
    public MovieDto deleteScreening(ScreeningDto screeningDto) {
        ScreeningEntity convertedScreening = screeningDtoToScreeningEntity(screeningDto);
        MovieEntity movie = convertedScreening.getMovie();
        ScreeningEntity screening = screeningRepository.findByMovieAndRoomAndDate(
                movie,
                convertedScreening.getRoom(),
                convertedScreening.getScreeningTime()
        ).orElseThrow(() -> new IllegalArgumentException("There's no screening with these attributes!"));

        return new MovieDto(
                movie.getTitle(),
                movie.getGenre(),
                movie.getLength()
        );
    }

    @Override
    public List<ScreeningDto> getScreeningList() {
        List<ScreeningEntity> screeningEntityList = screeningRepository.findAll();

        return screeningEntityListToScreeningDtoList(screeningEntityList);
    }


    private ScreeningEntity screeningDtoToScreeningEntity(ScreeningDto screeningDto) {
        MovieEntity movie;
        RoomEntity room;
        Date screeningTime = null;

        movie = movieRepository.findByTitle(screeningDto.getMovieTitle())
                .orElseThrow(() -> new IllegalArgumentException("There's no movie by that title!"));

        room = roomRepository.findByName(screeningDto.getRoomName())
                .orElseThrow(() -> new IllegalArgumentException("There's no room by that name!"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            screeningTime = formatter.parse(screeningDto.getScreeningTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new ScreeningEntity(movie, room, screeningTime);
    }

    private List<ScreeningDto> screeningEntityListToScreeningDtoList(List<ScreeningEntity> screeningEntityList) {
        List<ScreeningDto> screeningDtoList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        for (ScreeningEntity screening : screeningEntityList) {
            screeningDtoList.add(new ScreeningDto(
                    screening.getMovie().getTitle(),
                    screening.getRoom().getName(),
                    formatter.format(screening.getScreeningTime())
            ));
        }

        return screeningDtoList;
    }
}
