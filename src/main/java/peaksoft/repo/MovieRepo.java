package peaksoft.repo;

import peaksoft.entity.Movie;
import peaksoft.entity.ShowTime;

import java.util.List;

public interface MovieRepo {
    List<Movie> findAll();
    List<Movie> getMoviesForToday();
    List<Movie> getUpcomingMovies();

    Movie findById(Long id);

    List<ShowTime> getShowTimesForMovie(Long movieId);

    void save(Movie movie);

    void deleteById(Long movieId);

    boolean existById(Long movieId);

    void update(Movie movie);
}
