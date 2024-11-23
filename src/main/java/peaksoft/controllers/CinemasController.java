package peaksoft.controllers;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Cinema;

import peaksoft.entity.Hall;
import peaksoft.entity.Movie;
import peaksoft.entity.ShowTime;
import peaksoft.repo.ShowTimeRepo;
import peaksoft.services.CinemaService;
import peaksoft.services.HallService;
import peaksoft.services.MovieService;
import peaksoft.services.ShowTimeService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cinemas")
public class CinemasController {
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final HallService hallService;
    private final ShowTimeService showTimeService;


    @GetMapping()
    public String cinemas(Model model) {
        List<Cinema> cinemas = cinemaService.getAll();
        model.addAttribute("cinemas", cinemas);
        return "cinemas";
    }
    @GetMapping("/{cinemaId}")
    public String cinemaDetails(@PathVariable Long cinemaId, Model model) {
        Cinema cinema = cinemaService.getCinemaById(cinemaId);
        List<Hall> hallsForCinema = hallService.findByCinemaId(cinemaId);

        Map<Hall, Map<Movie, List<ShowTime>>> hallMoviesMap = new LinkedHashMap<>();

        for (Hall hall : hallsForCinema) {
            List<ShowTime> showTimes = hall.getShowTimes();
            Map<Movie, List<ShowTime>> movieShowTimesMap = new HashMap<>();

            for (ShowTime showTime : showTimes) {
                Movie movie = showTime.getMovie();
                movieShowTimesMap.computeIfAbsent(movie, k -> new ArrayList<>()).add(showTime);
            }
            hallMoviesMap.put(hall, movieShowTimesMap);
        }

        model.addAttribute("cinema", cinema);
        model.addAttribute("hallMoviesMap", hallMoviesMap);
        System.out.println(hallMoviesMap);

        return "cinema-details";
    }



    @GetMapping("/showTimes")
    public String getShowTimes(@RequestParam("movieId") Long movieId, Model model) {

        Movie movie = movieService.findMovieById(movieId);

        List<Hall> halls = movie.getCinemas().stream()
                .flatMap(cinema -> cinema.getHalls().stream())
                .collect(Collectors.toList());

        model.addAttribute("movie", movie);
        model.addAttribute("halls", halls);

        return "movieShowTimes";
    }
//    @GetMapping("/{cinemaId}/halls")
//    public String cinemaHalls(@PathVariable Long cinemaId, @RequestParam("movieId") Long movieId, Model model) {
//        Cinema cinema = cinemaService.getCinemaById(cinemaId);
//        List<Hall> halls = hallService.getHallsWithShowTimesForMovie(movieId);
//        model.addAttribute("cinema", cinema);
//        model.addAttribute("halls", halls);
//        return "cinema-halls"; // Страница с залами для выбранного фильма
//    }
//
//    @GetMapping("/hallsWithShowTimes")
//    public String getHallsWithShowTimes(@RequestParam("movieId") Long movieId, Model model) {
//        List<Hall> halls = hallService.getHallsWithShowTimesForMovie(movieId);
//        model.addAttribute("halls", halls);
//        return "partials/hallsWithShowTimes";
//    }



//    @GetMapping("/cinema/{cinemaId}/halls")
//    public String getCinemaHalls(@PathVariable Long cinemaId, Model model) {
//        Cinema cinema = cinemaService.getCinemaById(cinemaId);
//        List<Hall> halls = hallService.findByCinemaId(cinemaId);
//        model.addAttribute("cinema", cinema);
//        model.addAttribute("halls", halls);
//        return "cinema-halls";
//    }



}
