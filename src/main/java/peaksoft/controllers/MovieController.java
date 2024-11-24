package peaksoft.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import peaksoft.entity.*;
import peaksoft.repo.TicketRepo;
import peaksoft.services.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final ShowTimeService showTimeService;

    private final TicketRepo ticketRepo;
    private final UserService userService;
    private final CinemaService cinemaService;
    private final HallService hallService;

    @GetMapping
    public String index(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        
        List<Movie> moviesForToday = movieService.getMoviesForToday();
        System.out.println("Фильмов на сегодня: " + moviesForToday.size());
        model.addAttribute("moviesForToday", moviesForToday);

        List<Movie> upcomingMovies = movieService.getUpcomingMovies();

        model.addAttribute("upcomingMovies", upcomingMovies);

        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "homePage2";
    }

    @GetMapping("/all")
    public String listMoviesForUser(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movieListUser";
    }
    @GetMapping("/{movieId}/showTimes")
    public String getShowTimesForMovie(@PathVariable Long movieId, Model model) {

        Movie movie = movieService.findMovieById(movieId);
        MovieInfo mi = movie.getMovieInfo();
        List<Cinema> cinemas = cinemaService.getCinemasForMovie(movieId);
        for (Cinema cinema : cinemas) {
            List<Hall> hallsForMovie = hallService.getHallsWithShowTimesForMovie(movieId, cinema.getId());
            for (Hall hall : hallsForMovie) {

                List<ShowTime> showTimes = showTimeService.getShowTimesForHallAndMovie(hall.getId(), movieId);
                hall.setShowTimes(showTimes);
            }
            cinema.setHalls(hallsForMovie);
        }
        
        model.addAttribute("movie", movie);
        model.addAttribute("cinemas", cinemas);
        model.addAttribute("movieInfo", mi);

        return "showTimes";
    }

    @GetMapping("/today")
    @ResponseBody
    public List<Movie> getTodayMovies() {
        return movieService.getMoviesForToday();
    }

    @GetMapping("/upcoming")
    @ResponseBody
    public List<Movie> getUpcomingMovies() {
        return movieService.getUpcomingMovies();
    }

    @GetMapping("/details/{id}")
    public String getMovieDetails(@PathVariable Long id, Model model) {
        Movie movie = movieService.findMovieById(id);
        if (movie == null) {
            System.out.println("Фильм не найден: " + id);
            return "redirect:/movies/all";
        }
        System.out.println("Найден фильм: " + movie.getMovieName());
        model.addAttribute("movie", movie);
        return "movieDetails";
    }

    @GetMapping("/details/{id}/buy")
    public String buyTicket(@PathVariable Long id, Model model) {
        Movie movie = movieService.findMovieById(id);
        if (movie == null) {
            return "redirect:/movies/all";
        }
        model.addAttribute("movie", movie);
        return "buyTicket";
    }


}
