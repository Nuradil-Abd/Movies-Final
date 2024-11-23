package peaksoft.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Cinema;
import peaksoft.entity.Hall;
import peaksoft.entity.Movie;
import peaksoft.repo.CinemaRepo;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CinemaRepoImpl implements CinemaRepo {
    private final EntityManager makeEntityManager;
    @PersistenceContext
    private EntityManager entityManager;

    public CinemaRepoImpl(EntityManager makeEntityManager) {
        this.makeEntityManager = makeEntityManager;
    }

    public List<Hall> getHallsForMovieInCinema(Long movieId, Long cinemaId) {

        String jpql = "SELECT h FROM Hall h " +
                      "JOIN h.cinema c " +
                      "JOIN h.showTimes st " +
                      "WHERE c.id = :cinemaId " +
                      "AND st.movie.id = :movieId";

        List<Hall> targetHalls = entityManager.createQuery(jpql, Hall.class)
                .setParameter("cinemaId", cinemaId)
                .setParameter("movieId", movieId)
                .getResultList();
        
        return targetHalls;
    }


    @Override
    public List<Cinema> getAll() {
        return entityManager.createQuery("SELECT c FROM Cinema c", Cinema.class)
                .getResultList();
    }

    @Override
    public Cinema getCinemaById(Long cinemaId) {
        return  entityManager.find(Cinema.class, cinemaId);
    }

    @Override
    public List<Movie> getMoviesByCinemaId(Long cinemaId) {
        String query = "SELECT DISTINCT m FROM Movie m " +
                       "JOIN m.cinemas c " +
                       "LEFT JOIN FETCH m.showTimes st " +
                       "LEFT JOIN FETCH st.hall " +
                       "WHERE c.id = :cinemaId";
        return entityManager.createQuery(query, Movie.class)
                .setParameter("cinemaId", cinemaId)
                .getResultList();
    }

    @Override
    public List<Hall> getHallsWithShowTimesForMovie(Long movieId) {
        String jpql = "SELECT h FROM Hall h JOIN h.showTimes st WHERE st.movie.id = :movieId";
        try {
            return entityManager.createQuery(jpql, Hall.class)
                    .setParameter("movieId", movieId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }





    @Override
    public Movie findMovieById(Long movieId) {
        try {
            return entityManager.find(Movie.class, movieId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long cinemaId) {
        entityManager.remove(entityManager.find(Cinema.class, cinemaId));
    }

    @Override
    public void save(Cinema cinema) {

        if (cinema.getId() == null) {
            entityManager.persist(cinema);
        } else {
            entityManager.merge(cinema);
        }
    }

    @Override
    public List<Cinema> getCinemaForMovie(Long movieId) {
        return entityManager.createQuery(
                        "SELECT c FROM Cinema c JOIN c.movies m WHERE m.id = :movieId", Cinema.class)
                .setParameter("movieId", movieId)
                .getResultList();
    }

    @Override
    public void update(Cinema cinema) {
        entityManager.merge(cinema);
    }
}
