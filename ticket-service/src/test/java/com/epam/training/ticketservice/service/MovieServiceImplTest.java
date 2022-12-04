package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entity.MovieEntity;
import com.epam.training.ticketservice.model.MovieDto;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl testMovieService;

    private final MovieEntity TEST_MOVIE_ENTITY = new MovieEntity("Lord of the Rings", "fantasy", 178);

    private final MovieDto TEST_MOVIE_DTO = new MovieDto("Lord of the Rings", "fantasy", 178);

    @Test
    void testCreateMovieShouldStoreGivenMovieWhenInputIsValid() {
        // Given
        Mockito.when(movieRepository.save(TEST_MOVIE_ENTITY)).thenReturn(TEST_MOVIE_ENTITY);

        // When
        testMovieService.createMovie(TEST_MOVIE_DTO);

        // Then
        Mockito.verify(movieRepository).save(TEST_MOVIE_ENTITY);
    }

    @Test
    void testUpdateMovieShouldUpdateGivenMovieWhenInputIsValid() {
        // Given
        Mockito.when(movieRepository.findByTitle(TEST_MOVIE_ENTITY.getTitle()))
                .thenReturn(Optional.of(TEST_MOVIE_ENTITY));

        // When
        testMovieService.updateMovie(TEST_MOVIE_DTO);

        // Then
        Mockito.verify(movieRepository).findByTitle(TEST_MOVIE_ENTITY.getTitle());
    }

    @Test
    void testUpdateMovieShouldThrowErrorWhenInputIsInvalid() {
        // Given
        Mockito.when(movieRepository.findByTitle(TEST_MOVIE_ENTITY.getTitle())).thenReturn(Optional.empty());

        // When Then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> testMovieService.updateMovie(TEST_MOVIE_DTO));
        Mockito.verify(movieRepository).findByTitle(TEST_MOVIE_ENTITY.getTitle());
    }

    @Test
    void testDeleteMovieShouldBeDeletedGivenMovieWhenInputIsValid() {
        // Given
        Mockito.when(movieRepository.findByTitle(TEST_MOVIE_ENTITY.getTitle()))
                .thenReturn(Optional.of(TEST_MOVIE_ENTITY));

        // When
        testMovieService.deleteMovie(TEST_MOVIE_DTO.getTitle());

        // Then
        Mockito.verify(movieRepository).findByTitle(TEST_MOVIE_ENTITY.getTitle());
    }

    @Test
    void testDeleteMovieShouldThrowErrorWhenInputIsInvalid() {
        // Given
        Mockito.when(movieRepository.findByTitle(TEST_MOVIE_ENTITY.getTitle())).thenReturn(Optional.empty());

        // When Then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> testMovieService.deleteMovie(TEST_MOVIE_DTO.getTitle()));
        Mockito.verify(movieRepository).findByTitle(TEST_MOVIE_ENTITY.getTitle());
    }

    @Test
    void testGetMovieListShouldReturnListWithOneElement() {
        //Given
        Mockito.when(movieRepository.findAll()).thenReturn(List.of(TEST_MOVIE_ENTITY));
        List<MovieDto> expected = List.of(TEST_MOVIE_DTO);

        //When
        List<MovieDto> actual = testMovieService.getMovieList();

        //Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(movieRepository).findAll();
    }



}
