package peaksoft.services;

import peaksoft.entity.MovieInfo;

import java.util.List;
import java.util.Optional;

public interface MovieInfoService {
    MovieInfo saveMovieInfo(MovieInfo movieInfo);
    Optional<MovieInfo> findMovieInfoById(Long id);
    List<MovieInfo> findAllMovieInfos();
    MovieInfo updateMovieInfo(Long id, MovieInfo updatedMovieInfo);
    void deleteMovieInfo(Long id);
}
