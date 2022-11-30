package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void createMovie(MovieDto movieDto);

    void updateMovie(String title, MovieDto movieDto);

    void deleteMovie(String title);

    Optional<MovieDto> findMovieByTitle(String title);

    List<MovieDto> getMovieList();

}
