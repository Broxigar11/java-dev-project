package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.entity.MovieEntity;
import com.epam.training.ticketservice.entity.RoomEntity;
import com.epam.training.ticketservice.entity.ScreeningEntity;
import com.epam.training.ticketservice.model.ScreeningDto;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    @Override
    public String createScreening(ScreeningDto screeningDto) {
        ScreeningEntity screening = screeningDtoToScreeningEntity(screeningDto);
        Date screeningStartDate = screening.getScreeningTime();
        Date screeningEndDate = addMinutesToDate(screeningStartDate, screening.getMovie().getLength());
        Date breakBeforeScreeningStartDate = addMinutesToDate(screeningStartDate, -10);
        Date breakAfterScreeningEndDate = addMinutesToDate(screeningEndDate, 10);

        List<ScreeningEntity> potentiallyOverlappingScreenings = screeningRepository.findAllByRoom(screening.getRoom());

        for (ScreeningEntity screeningEntity : potentiallyOverlappingScreenings) {
            Date entityScreeningTimeStart = screeningEntity.getScreeningTime();
            Date entityScreeningTimeEnd = addMinutesToDate(entityScreeningTimeStart,
                    screeningEntity.getMovie().getLength());

            if (isIntervalOverlappingWithGivenInterval(screeningStartDate, screeningEndDate,
                   entityScreeningTimeStart, entityScreeningTimeEnd)) {
                return "There is an overlapping screening";
            }
            if (isIntervalOverlappingWithGivenInterval(breakBeforeScreeningStartDate, breakAfterScreeningEndDate,
                    entityScreeningTimeStart, entityScreeningTimeEnd)) {
                return "This would start in the break period after another screening in this room";
            }
        }

        screeningRepository.save(screening);

        return "Screening " + screeningEntityToString(screening) + " created";
    }

    @Override
    public String deleteScreening(ScreeningDto screeningDto) {
        ScreeningEntity convertedScreening = screeningDtoToScreeningEntity(screeningDto);
        MovieEntity movie = convertedScreening.getMovie();
        ScreeningEntity screening = screeningRepository.findByMovieAndRoomAndScreeningTime(
                movie,
                convertedScreening.getRoom(),
                convertedScreening.getScreeningTime()
        ).orElseThrow(() -> new IllegalArgumentException("There's no screening with these properties!"));

        screeningRepository.delete(screening);

        return "Screening " + screeningEntityToString(screening) + " deleted";
    }

    @Override
    public String getScreeningList() {
        List<ScreeningEntity> screeningEntityList = screeningRepository.findAll();

        return screeningEntityListToString(screeningEntityList);
    }

    private ScreeningEntity screeningDtoToScreeningEntity(ScreeningDto screeningDto) {
        MovieEntity movie;
        RoomEntity room;
        Date screeningTime;

        movie = movieRepository.findByTitle(screeningDto.getMovieTitle())
                .orElseThrow(() -> new IllegalArgumentException("There's no movie by that title!"));

        room = roomRepository.findByName(screeningDto.getRoomName())
                .orElseThrow(() -> new IllegalArgumentException("There's no room by that name!"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            screeningTime = formatter.parse(screeningDto.getScreeningTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new ScreeningEntity(movie, room, screeningTime);
    }

    private String screeningEntityListToString(List<ScreeningEntity> screeningEntityList) {
        if (screeningEntityList.isEmpty()) {
            return "There are no screenings";
        }

        String response = "";

        for (int i = 0; i < screeningEntityList.size(); i++) {
            response += screeningEntityToString(screeningEntityList.get(i));
            if (i < screeningEntityList.size() - 1) {
                response += "\n";
            }
        }

        return response;
    }

    private String screeningEntityToString(ScreeningEntity screening) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        MovieEntity movie = screening.getMovie();
        return  movie.getTitle()
                + " (" + movie.getGenre()
                + ", " + movie.getLength()
                + " minutes), screened in room " + screening.getRoom().getName()
                + ", at " + formatter.format(screening.getScreeningTime());
    }

    private Date addMinutesToDate(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);

        return cal.getTime();
    }

    private boolean isIntervalOverlappingWithGivenInterval(Date startOfFirst, Date endOfFirst,
                                                           Date startOfSecond, Date endOfSecond) {
        return !(endOfFirst.before(startOfSecond) || endOfSecond.before(startOfFirst));
    }

}