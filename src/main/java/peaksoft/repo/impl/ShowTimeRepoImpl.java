package peaksoft.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Cinema;
import peaksoft.entity.Hall;
import peaksoft.entity.Movie;
import peaksoft.entity.ShowTime;
import peaksoft.repo.ShowTimeRepo;

import java.sql.Time;
import java.util.List;

@Repository
@Transactional
public class ShowTimeRepoImpl implements ShowTimeRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ShowTime> findAll() {
        TypedQuery<ShowTime> query = entityManager.createQuery("SELECT st FROM ShowTime st", ShowTime.class);
        return query.getResultList();
    }

    @Override
    public ShowTime findById(Long id) {
        return entityManager.find(ShowTime.class, id);
    }

    @Override
    public List<ShowTime> findByHallId(Long hallId) {
        TypedQuery<ShowTime> query = entityManager.createQuery(
                "SELECT st FROM ShowTime st WHERE st.hall.id = :hallId", ShowTime.class);
        query.setParameter("hallId", hallId);
        return query.getResultList();
    }

    @Override
    public List<ShowTime> findByMovieId(Long movieId) {
        TypedQuery<ShowTime> query = entityManager.createQuery(
                "SELECT st FROM ShowTime st WHERE st.movie.id = :movieId", ShowTime.class);
        query.setParameter("movieId", movieId);
        return query.getResultList();
    }



    @Override
    public void save(ShowTime showTime) {

        if (showTime.getId() == null) {

            Movie managedMovie = entityManager.merge(showTime.getMovie());
            Hall managedHall = entityManager.merge(showTime.getHall());


            Cinema cinema = managedHall.getCinema();
            if (!managedMovie.getCinemas().contains(cinema)) {
                managedMovie.getCinemas().add(cinema);
            }


            showTime.setMovie(managedMovie);
            showTime.setHall(managedHall);
            entityManager.persist(showTime);
        } else {

            entityManager.merge(showTime);
        }
    }

    @Override
    public void deleteById(Long id) {
        ShowTime showTime = findById(id);
        if (showTime != null) {
            entityManager.remove(showTime);
        }

    }

    @Override
    public List<ShowTime> getShowTimesForMovie(Long movieId) {
        String jpql = "SELECT st FROM ShowTime st WHERE st.movie.id = :movieId";
        return entityManager.createQuery(jpql, ShowTime.class)
                .setParameter("movieId", movieId)
                .getResultList();
    }

    @Override
    public List<ShowTime> getShowTimesForHallAndMovie(long hallId, Long movieId) {
        return entityManager.createQuery(
                        "SELECT st FROM ShowTime st WHERE st.hall.id = :hallId AND st.movie.id = :movieId", ShowTime.class)
                .setParameter("hallId", hallId)
                .setParameter("movieId", movieId)
                .getResultList();
    }

    @Override
    public ShowTime findByMovieAndHallAndStartTime(Movie movie, Hall hall, Time time) {
        String query = "SELECT st FROM ShowTime st " +
                       "WHERE st.movie = :movie " +
                       "AND st.hall = :hall " +
                       "AND st.startTime = :startTime";
        try {
            return entityManager.createQuery(query, ShowTime.class)
                    .setParameter("movie", movie)
                    .setParameter("hall", hall)
                    .setParameter("startTime", time)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<ShowTime> findByCinemaAndMovie(Long cinemaId, Long movieId) {
        return entityManager.createQuery(
                        "SELECT st FROM ShowTime st " +
                        "JOIN st.hall h " +
                        "JOIN h.cinema c " +
                        "WHERE c.id = :cinemaId AND st.movie.id = :movieId", ShowTime.class)
                .setParameter("cinemaId", cinemaId)
                .setParameter("movieId", movieId)
                .getResultList();
    }



}
