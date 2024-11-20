package peaksoft.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Movie;
import peaksoft.entity.ShowTime;
import peaksoft.repo.MovieRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional

public class MovieRepoImpl implements MovieRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Movie> findAll() {
        return entityManager.createQuery("select m from Movie m", Movie.class).getResultList() ;
    }

//    @Override
//    public List<Movie> getMoviesForToday() {
//        LocalDate today = LocalDate.now();
//        return findAll().stream()
//                .filter(movie -> !movie.getReleaseDate().isAfter(today))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Movie> getUpcomingMovies() {
//        LocalDate today = LocalDate.now();
//        return findAll().stream()
//                .filter(movie -> movie.getReleaseDate().isAfter(today))
//                .collect(Collectors.toList());
//    }

//    @Override
//    public List<Movie> getMoviesForToday() {
//        LocalDate today = LocalDate.now();
//
//        return entityManager.createQuery("select m from Movie m where m.releaseDate <= :today", Movie.class)
//                .setParameter("today", today)
//                .getResultList();
//    }
//
//    @Override
//    public List<Movie> getUpcomingMovies() {
//        LocalDate today = LocalDate.now();
//
//        return entityManager.createQuery("select m from Movie m where m.releaseDate > :today", Movie.class)
//                .setParameter("today", today)
//                .getResultList();
//    }

    @Override
    public List<Movie> getMoviesForToday() {
        String query = "SELECT m FROM Movie m WHERE m.releaseDate >= :today";
        return entityManager.createQuery(query, Movie.class)
                .setParameter("today", LocalDate.now())
                .getResultList();
    }

   @Override
    public List<Movie> getUpcomingMovies() {
        String query = "SELECT m FROM Movie m WHERE m.releaseDate > :today";
        return entityManager.createQuery(query, Movie.class)
                .setParameter("today", LocalDate.now())
                .getResultList();
    }

    public Movie findById(Long id) {
        Movie movie = entityManager.find(Movie.class, id);
        if (movie == null) {
            System.out.println("Фильм с ID " + id + " не найден");
        }
        return movie;
    }

    @Override
    public List<ShowTime> getShowTimesForMovie(Long movieId) {
        String query = "SELECT s FROM ShowTime s WHERE s.movie.id = :movieId";
        return entityManager.createQuery(query, ShowTime.class)
                .setParameter("movieId", movieId)
                .getResultList();
    }

    @Override
    public void save(Movie movie) {
        if (movie.getId() == null) {
            entityManager.persist(movie);
        } else {
            entityManager.merge(movie);
        }
    }

    @Override
    public void deleteById(Long movieId) {
        entityManager.remove(entityManager.find(Movie.class, movieId));
    }

    @Override
    public boolean existById(Long movieId) {
        return entityManager.find(Movie.class, movieId) != null;
    }

    @Override
    public void update(Movie movie) {
        Movie existingMovie = entityManager.find(Movie.class, movie.getId());

//        Movie existingMovie = entityManager.createQuery("SELECT m FROM Movie m WHERE m.id = :id", Movie.class)
//                .setParameter("id", movie.getId())
//                .getSingleResult();
        existingMovie.setMovieName(movie.getMovieName());
        existingMovie.setImage(movie.getImage());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setDuration(movie.getDuration());
        existingMovie.setReleaseDate(movie.getReleaseDate());
        existingMovie.setAgeRating(movie.getAgeRating());
        existingMovie.setTrailerUrl(movie.getTrailerUrl());
        entityManager.merge(existingMovie);
    }
}
