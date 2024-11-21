package peaksoft.services;

import peaksoft.entity.Cinema;
import peaksoft.entity.Hall;
import peaksoft.entity.Movie;

import java.sql.Time;
import java.util.List;

public interface CinemaService {
    List<Cinema> getAll();

    Cinema getCinemaById(Long cinemaId);

    List<Movie> getMoviesByCinemaId(Long cinemaId);

    Movie findMovieById(Long movieId);

    List<Hall> getHallsWithShowTimesForMovie(Long movieId);

    void deleteById(Long cinemaId);

    void save(Cinema cinema);

    List<Cinema> getCinemasForMovie(Long movieId);

    void update(Cinema cinema);


}
