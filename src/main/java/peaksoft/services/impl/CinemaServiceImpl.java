package peaksoft.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Cinema;
import peaksoft.entity.Hall;
import peaksoft.entity.Movie;
import peaksoft.repo.CinemaRepo;
import peaksoft.services.CinemaService;

import java.sql.Time;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepo cinemaRepo;


    @Override
    public List<Cinema> getAll() {
        return cinemaRepo.getAll() ;
    }

    @Override
    public Cinema getCinemaById(Long cinemaId) {
        return cinemaRepo.getCinemaById(cinemaId);
    }

    @Override
    public List<Movie> getMoviesByCinemaId(Long cinemaId) {
        return cinemaRepo.getMoviesByCinemaId(cinemaId);
    }

    @Override
    public Movie findMovieById(Long movieId) {
        return cinemaRepo.findMovieById(movieId);
    }

    @Override
    public List<Hall> getHallsWithShowTimesForMovie(Long movieId) {
        return cinemaRepo.getHallsWithShowTimesForMovie(movieId);
    }

    @Override
    public void deleteById(Long cinemaId) {
        cinemaRepo.deleteById(cinemaId);
    }

    @Override
    public void save(Cinema cinema) {
         cinemaRepo.save(cinema);
    }

    @Override
    public List<Cinema> getCinemasForMovie(Long movieId) {
        return cinemaRepo.getCinemaForMovie(movieId);
    }

    @Override
    public void update(Cinema cinema) {
        cinemaRepo.update(cinema);
    }

}
