package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.entity.MovieEntity;
import com.epam.training.ticketservice.model.MovieDto;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public void createMovie(MovieDto movieDto) {
        movieRepository.save(new MovieEntity(
                    movieDto.getTitle(),
                    movieDto.getGenre(),
                    movieDto.getLength()
                ));
    }

    @Override
    public void updateMovie(String title, MovieDto movieDto) {
        MovieEntity movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("There's no movie by this title!"));

        movieRepository.updateMovieEntity(
                movieDto.getTitle(),
                movieDto.getGenre(),
                movieDto.getLength()
        );
    }

    @Override
    public void deleteMovie(String title) {
        MovieEntity movie = movieRepository.findByTitle(title).
                orElseThrow(() -> new IllegalArgumentException("There's no movie by this title!"));

        movieRepository.delete(movie);
    }

    @Override
    public Optional<MovieDto> findMovieByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public List<MovieDto> getMovieList() {
        List<MovieEntity> movieList = movieRepository.findAll();

        return movieEntityListToMovieDtoList(movieList);
    }

    private List<MovieDto> movieEntityListToMovieDtoList(List<MovieEntity> movieEntityList) {
        List<MovieDto> movieDtoList = new ArrayList<>();

        for (MovieEntity entity : movieEntityList) {
            movieDtoList.add(new MovieDto(
                    entity.getTitle(),
                    entity.getGenre(),
                    entity.getLength()
            ));
        }
        return movieDtoList;
    }
}
