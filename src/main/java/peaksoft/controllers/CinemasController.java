package peaksoft.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.*;

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
    public String cinemas(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        List<Cinema> cinemas = cinemaService.getAll();
        model.addAttribute("cinemas", cinemas);
        return "cinemas";
    }
    @GetMapping("/{cinemaId}")
    public String cinemaDetails(@PathVariable Long cinemaId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
//        if (user == null) {
//            return "redirect:/signIn";
//        }
        model.addAttribute("currentUser", user);
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
    public String getShowTimes(@RequestParam("movieId") Long movieId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/signIn";
        }
        model.addAttribute("currentUser", user);

        Movie movie = movieService.findMovieById(movieId);

        List<Hall> halls = movie.getCinemas().stream()
                .flatMap(cinema -> cinema.getHalls().stream())
                .collect(Collectors.toList());

        model.addAttribute("movie", movie);
        model.addAttribute("halls", halls);

        return "movieShowTimes";
    }


}
