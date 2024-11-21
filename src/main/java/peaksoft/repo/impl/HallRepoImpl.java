package peaksoft.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Cinema;
import peaksoft.entity.Hall;
import peaksoft.repo.HallRepo;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional

public class HallRepoImpl implements HallRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Hall> findAll() {
        TypedQuery<Hall> query = entityManager.createQuery("SELECT h FROM Hall h", Hall.class);
        return query.getResultList();
    }

    @Override
    public Hall findById(Long id) {
        return entityManager.find(Hall.class, id);
    }

    @Override
    public Hall findByName(String name) {
        TypedQuery<Hall> query = entityManager.createQuery("SELECT h FROM Hall h WHERE h.name = :name", Hall.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public Hall save(Hall hall) {
//        if (hall.getId() == null) {
//            entityManager.persist(hall);
//        } else {hall =
            return entityManager.merge(hall);
//        }
//        return hall;
    }

    @Override
    public List<Hall> findByShowTimesMovieId(Long movieId) {
        String query = """
            SELECT DISTINCT h 
            FROM Hall h 
            JOIN h.showTimes s 
            WHERE s.movie.id = :movieId
        """;

        return entityManager.createQuery(query, Hall.class)
                .setParameter("movieId", movieId)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Hall hall = entityManager.find(Hall.class, id);

        if (hall == null) {
            throw new IllegalArgumentException("Hall not found with ID: " + id);
        }
        Cinema cinema = hall.getCinema();
        if (cinema != null) {
            cinema.getHalls().remove(hall);
            hall.setCinema(null);
        }
        hall.getShowTimes().forEach(showTime -> {
            showTime.getTickets().clear();
            entityManager.remove(showTime);
        });
        hall.getShowTimes().clear();
        entityManager.remove(hall);

        System.out.println("Hall deleted successfully");
    }

    @Override
    public List<Hall> findByCinemaId(Long cinemaId) {
        String query = "SELECT h FROM Hall h WHERE h.cinema.id = :cinemaId";
        return entityManager.createQuery(query, Hall.class)
                .setParameter("cinemaId", cinemaId)
                .getResultList();
    }




    @Override
    public List<Hall> getHallsForCinemaAndMovie(Long cinemaId, Long movieId) {
        return entityManager.createQuery(
                        "SELECT h FROM Hall h " +
                        "JOIN h.showTimes s " +
                        "WHERE s.movie.id = :movieId AND h.cinema.id = :cinemaId", Hall.class)
                .setParameter("movieId", movieId)
                .setParameter("cinemaId", cinemaId)
                .getResultList();
    }

    public List<Hall> getHallsWithShowTimesForMovie(Long movieId, Long cinemaId) {
        // Возвращаем все залы для кинотеатра с указанным фильмом
        return entityManager.createQuery(
                        "SELECT h FROM Hall h " +
                        "JOIN h.showTimes s " +
                        "WHERE s.movie.id = :movieId AND h.cinema.id = :cinemaId", Hall.class)
                .setParameter("movieId", movieId)
                .setParameter("cinemaId", cinemaId)
                .getResultList();
    }


}
