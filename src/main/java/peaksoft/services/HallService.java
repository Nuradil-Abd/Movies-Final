package peaksoft.services;

import peaksoft.entity.Hall;

import java.util.List;

public interface HallService {
    List<Hall> getAllHalls();
    Hall findHallById(Long id);
    Hall findByName(String name);
    List<Hall> getHallsWithShowTimesForMovie(Long movieId, Long cinemaId);
    Hall saveHall(Hall hall);
    void deleteHallById(Long id);

    List<Hall> findByCinemaId(Long cinemaId);
     Hall createHall(Hall hall);
    Hall updateHall(Long hallId, Hall updatedHall);

    List<Hall> getHallsForCinemaAndMovie(Long id, Long movieId);
}
