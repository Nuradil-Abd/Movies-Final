package peaksoft.services;

import peaksoft.entity.Movie;
import peaksoft.entity.ShowTime;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();

    List<Movie> getMoviesForToday();

    List<Movie> getUpcomingMovies();

    Movie findMovieById(Long id);

    List<ShowTime> getShowTimesForMovie(Long movieId);

    void save(Movie movie);

    void deleteById(Long movieId);

    boolean existsById(Long movieId);


    void update(Movie movie);
}
