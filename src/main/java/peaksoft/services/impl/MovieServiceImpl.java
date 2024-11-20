package peaksoft.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Movie;
import peaksoft.entity.ShowTime;
import peaksoft.repo.MovieRepo;
import peaksoft.services.MovieService;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepo movieRepo;

    @Override
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    @Override
    public List<Movie> getMoviesForToday() {
        return movieRepo.getMoviesForToday();
    }

    @Override
    public List<Movie> getUpcomingMovies() {
        return movieRepo.getUpcomingMovies();
    }

    @Override
    public Movie findMovieById(Long id) {
        return  movieRepo.findById(id);
    }

    @Override
    public List<ShowTime> getShowTimesForMovie(Long movieId) {
        return movieRepo.getShowTimesForMovie(movieId);
    }

    @Override
    public void save(Movie movie) {
         movieRepo.save(movie);
    }

    @Override
    public void deleteById(Long movieId) {
        movieRepo.deleteById(movieId);
    }

    @Override
    public boolean existsById(Long movieId) {
        return movieRepo.existById(movieId);
    }

    @Override
    public void update(Movie movie) {
        movieRepo.update(movie);
    }
}
