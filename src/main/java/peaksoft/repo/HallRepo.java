package peaksoft.repo;

import peaksoft.entity.Hall;

import java.util.List;

public interface HallRepo {

    List<Hall> findAll();

    Hall findById(Long id);

    Hall findByName(String name);

    Hall save(Hall hall);

    List<Hall> findByShowTimesMovieId(Long movieId);

    void deleteById(Long id);
    List<Hall> findByCinemaId(Long cinemaId);




    List<Hall> getHallsForCinemaAndMovie(Long id, Long movieId);

    List<Hall> getHallsWithShowTimesForMovie(Long movieId, Long cinemaId);
}
