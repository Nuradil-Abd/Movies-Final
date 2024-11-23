package peaksoft.repo;


import peaksoft.entity.Cinema;
import peaksoft.entity.Hall;
import peaksoft.entity.Movie;

import java.util.List;

public interface CinemaRepo {
    List<Hall> getHallsForMovieInCinema(Long movieId, Long cinemaId);

    List<Cinema> getAll();

    Cinema getCinemaById(Long cinemaId);

    List<Movie> getMoviesByCinemaId(Long cinemaId);

    List<Hall> getHallsWithShowTimesForMovie(Long movieId);

    Movie findMovieById(Long movieId);

    void deleteById(Long cinemaId);

    void save(Cinema cinema);

    List<Cinema> getCinemaForMovie(Long movieId);

    void update(Cinema cinema);
}
