package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.MovieDto;
import com.epam.training.ticketservice.model.ScreeningDto;

import java.util.List;

public interface ScreeningService {

    void createScreening(ScreeningDto screeningDto);

    MovieDto deleteScreening(ScreeningDto screeningDto);

    List<ScreeningDto> getScreeningList();


}
