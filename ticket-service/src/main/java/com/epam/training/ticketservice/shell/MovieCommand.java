package com.epam.training.ticketservice.shell;

import com.epam.training.ticketservice.entity.UserEntity;
import com.epam.training.ticketservice.model.MovieDto;
import com.epam.training.ticketservice.model.UserDto;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommand {

    private final UserService userService;

    private final MovieService movieService;

    @ShellMethod(key = "create movie", value = "creates a movie")
    @ShellMethodAvailability("isAdmin")
    public String createMovie(String title, String genre, Integer length) {
        MovieDto movieDto = new MovieDto(title, genre, length);
        movieService.createMovie(movieDto);
        return "Movie " + title + " created";
    }

    @ShellMethod(key = "update movie", value = "updates a movie")
    @ShellMethodAvailability("isAdmin")
    public String updateMovie(String title, String genre, Integer length) {
        MovieDto movieDto = new MovieDto(title, genre, length);
        movieService.updateMovie(title, movieDto);
        return "Movie " + title + " updated";
    }

    @ShellMethod(key = "delete movie", value = "deletes a movie")
    @ShellMethodAvailability("isAdmin")
    public String deleteMovie(String title) {
        movieService.deleteMovie(title);
        return "Movie " + title + " deleted";
    }

    @ShellMethod(key = "list movies", value = "list all movies")
    public String listMovies() {
        List<MovieDto> movieList = movieService.getMovieList();
        if (movieList.isEmpty()) {
            return "There are no movies at the moment";
        }
        String response = "";
        for (int i = 0; i < movieList.size(); i++) {
            MovieDto movie = movieList.get(i);
            response += movie.getTitle()
                    + " (" + movie.getGenre()
                    + ", " + movie.getLength()
                    + " minutes)";
            if (i < movieList.size() - 1) {
                response += "\n";
            }
        }

        return response;
    }

    public Availability isAdmin() {
        Optional<UserDto> userDto = userService.describe();

        return userDto.isPresent() && userDto.get().getRole() == UserEntity.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }

}
