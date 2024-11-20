package peaksoft.services;

import peaksoft.entity.Hall;
import peaksoft.entity.Movie;
import peaksoft.entity.ShowTime;

import java.sql.Time;
import java.util.List;

public interface ShowTimeService {
    List<ShowTime> getAllShowTimes();

    ShowTime findShowTimeById(Long id);

    List<ShowTime> getShowTimesByHallId(Long hallId);

    List<ShowTime> getShowTimesByMovieId(Long movieId);

    void saveShowTime(ShowTime showTime);

    void deleteShowTimeById(Long id);


    List<ShowTime> getShowTimesForMovie(Long movieId);

    List<ShowTime> getShowTimesForHallAndMovie(long id, Long movieId);

    ShowTime findByMovieAndHallAndStartTime(Movie movie, Hall hall, Time time);
    ShowTime prepareShowTimeData(Long movieId, Long hallId, String startTime, double price);
}
