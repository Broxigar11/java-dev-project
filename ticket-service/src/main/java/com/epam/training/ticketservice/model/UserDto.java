package com.epam.training.ticketservice.model;

import com.epam.training.ticketservice.entity.UserEntity;
import lombok.Value;

@Value
public class UserDto {

    String username;

    UserEntity.Role role;

}
