package com.epam.training.ticketservice.model;

import lombok.Value;

@Value
public class RoomDto {

    String name;

    Integer rows;

    Integer columns;

}
