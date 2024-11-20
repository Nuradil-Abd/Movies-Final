package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.MovieInfo;
import peaksoft.repo.MovieInfoRepo;
import peaksoft.services.MovieInfoService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieInfoServiceImpl implements MovieInfoService {

    private final MovieInfoRepo movieInfoRepo;


    @Override
    public MovieInfo saveMovieInfo(MovieInfo movieInfo) {
        return movieInfoRepo.saveMovieInfo(movieInfo);
    }

    @Override
    public Optional<MovieInfo> findMovieInfoById(Long id) {
        return movieInfoRepo.findMovieInfoById(id);
    }

    @Override
    public List<MovieInfo> findAllMovieInfos() {
        return movieInfoRepo.findAllMovieInfos();
    }

    @Override
    public MovieInfo updateMovieInfo(Long id, MovieInfo updatedMovieInfo) {
        return movieInfoRepo.updateMovieInfo(id, updatedMovieInfo);
    }

    @Override
    public void deleteMovieInfo(Long id) {
        movieInfoRepo.deleteMovieInfo(id);
    }
}
