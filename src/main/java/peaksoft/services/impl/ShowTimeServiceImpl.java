package peaksoft.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Cinema;
import peaksoft.entity.Hall;
import peaksoft.entity.Movie;
import peaksoft.entity.ShowTime;
import peaksoft.repo.ShowTimeRepo;
import peaksoft.services.CinemaService;
import peaksoft.services.HallService;
import peaksoft.services.MovieService;
import peaksoft.services.ShowTimeService;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional

public class ShowTimeServiceImpl implements ShowTimeService {
    private final ShowTimeRepo showTimeRepository;
    private final MovieService movieService;
    private final HallService hallService;
    private final CinemaService cinemaService;


    public List<ShowTime> getAllShowTimes() {
        return showTimeRepository.findAll();
    }

    public ShowTime findShowTimeById(Long id) {
        return showTimeRepository.findById(id);
    }

    public List<ShowTime> getShowTimesByHallId(Long hallId) {
        return showTimeRepository.findByHallId(hallId);
    }

    public List<ShowTime> getShowTimesByMovieId(Long movieId) {
        return showTimeRepository.findByMovieId(movieId);
    }

    public void saveShowTime(ShowTime showTime) {
        showTimeRepository.save(showTime);
    }

    public void deleteShowTimeById(Long id) {
        showTimeRepository.deleteById(id);
    }

    @Override
    public List<ShowTime> getShowTimesForMovie(Long movieId) {
        return showTimeRepository.getShowTimesForMovie(movieId);
    }

    @Override
    public List<ShowTime> getShowTimesForHallAndMovie(long id, Long movieId) {
        return showTimeRepository.getShowTimesForHallAndMovie(id, movieId);
    }

    @Override
    public ShowTime findByMovieAndHallAndStartTime(Movie movie, Hall hall, Time time) {
        return showTimeRepository.findByMovieAndHallAndStartTime(movie, hall, time);
    }

    @Transactional
    public ShowTime prepareShowTimeData(Long movieId, Long hallId, String startTime, double price) {
        Movie movie = movieService.findMovieById(movieId);
        Hall hall = hallService.findHallById(hallId);
        Cinema cinema = hall.getCinema();


        if (cinema.getMovies().stream().noneMatch(m -> m.getId().equals(movie.getId()))) {
            cinema.getMovies().add(movie);
            cinemaService.update(cinema);
        }

        ShowTime showTime = new ShowTime();
        showTime.setMovie(movie);
        showTime.setHall(hall);
        showTime.setStartTime(Time.valueOf(LocalTime.parse(startTime)));
        showTime.setPrice(price);

        return showTime;
    }

    @Override
    public boolean isTimeSlotOccupied(Long hallId, Time startTime, int movieDuration) {
        List<ShowTime> existingShowTimes = showTimeRepository.findByHallId(hallId);

        for (ShowTime existingShowTime : existingShowTimes) {

            Time existingStartTime = existingShowTime.getStartTime();
            int existingDuration = existingShowTime.getMovie().getDuration();

            Time existingEndTime = Time.valueOf(existingStartTime.toLocalTime().plusMinutes(existingDuration));

            Time newEndTime = Time.valueOf(startTime.toLocalTime().plusMinutes(movieDuration));

            if (!(newEndTime.before(existingStartTime) || startTime.after(existingEndTime))) {
                System.out.println("Overlap found: existing [" + existingStartTime + " - " + existingEndTime
                                   + "], new [" + startTime + " - " + newEndTime + "]");
                return true;
            }
        }
        return false;
    }
}