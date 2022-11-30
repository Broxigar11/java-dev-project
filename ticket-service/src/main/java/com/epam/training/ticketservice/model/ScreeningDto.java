package com.epam.training.ticketservice.model;

import lombok.Value;

@Value
public class ScreeningDto {

    String movieTitle;

    String roomName;

    String screeningTime;

}
