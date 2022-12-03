package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.ScreeningDto;

public interface ScreeningService {

    String createScreening(ScreeningDto screeningDto);

    String deleteScreening(ScreeningDto screeningDto);

    String getScreeningList();


}
