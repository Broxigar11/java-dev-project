package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entity.MovieEntity;
import com.epam.training.ticketservice.model.MovieDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    private final MovieEntity TEST_MOVIE_ENTITY = new MovieEntity("Lord of the Rings", "fantasy", 178);

    private final MovieDto TEST_MOVIE_DTO = new MovieDto("Lord of the Rings", "fantasy", 178);

}
