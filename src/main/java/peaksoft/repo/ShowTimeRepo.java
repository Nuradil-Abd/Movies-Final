package peaksoft.repo;

import peaksoft.entity.Hall;
import peaksoft.entity.Movie;
import peaksoft.entity.ShowTime;

import java.sql.Time;
import java.util.List;

public interface ShowTimeRepo {
    List<ShowTime> findAll();

    ShowTime findById(Long id);

    List<ShowTime> findByHallId(Long hallId);

    List<ShowTime> findByMovieId(Long movieId);

    void save(ShowTime showTime);

    void deleteById(Long id);

    List<ShowTime> getShowTimesForMovie(Long movieId);
    List<ShowTime> getShowTimesForHallAndMovie(long id, Long movieId);

    ShowTime findByMovieAndHallAndStartTime(Movie movie, Hall hall, Time time);

    List<ShowTime> findByCinemaAndMovie(Long cinemaId, Long movieId);

}
