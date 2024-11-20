package peaksoft.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.entity.MovieInfo;
import peaksoft.repo.MovieInfoRepo;

import java.util.List;
import java.util.Optional;

@Repository

@Transactional

public class MovieInfoRepoImpl implements MovieInfoRepo {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public MovieInfo saveMovieInfo(MovieInfo movieInfo) {
        return entityManager.merge(movieInfo);
    }

    @Override
    public Optional<MovieInfo> findMovieInfoById(Long id) {
        MovieInfo movieInfo = entityManager.find(MovieInfo.class, id);
        return Optional.ofNullable(movieInfo);
    }

    @Override
    public List<MovieInfo> findAllMovieInfos() {
        return entityManager.createQuery("select m from MovieInfo m", MovieInfo.class).getResultList();
    }

    @Override
    public MovieInfo updateMovieInfo(Long id, MovieInfo updatedMovieInfo) {
        MovieInfo existingMovieInfo = entityManager.find(MovieInfo.class, id);
        if (existingMovieInfo != null) {

            existingMovieInfo.setDirector(updatedMovieInfo.getDirector());
            existingMovieInfo.setActor(updatedMovieInfo.getActor());
            existingMovieInfo.setCountry(updatedMovieInfo.getCountry());
            existingMovieInfo.setLanguage(updatedMovieInfo.getLanguage());
            existingMovieInfo.setDescription(updatedMovieInfo.getDescription());

            return entityManager.merge(existingMovieInfo);
        } else {
            throw new IllegalArgumentException("MovieInfo with ID " + id + " not found.");
        }
    }

    @Override
    public void deleteMovieInfo(Long id) {
        MovieInfo movieInfo = entityManager.find(MovieInfo.class, id);
        if (movieInfo != null) {

            entityManager.remove(movieInfo);
        } else {
            throw new IllegalArgumentException("MovieInfo with ID " + id + " not found.");
        }

    }
}
