package peaksoft.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import peaksoft.entity.*;
import peaksoft.services.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final MovieService movieService;
    private final CinemaService cinemaService;
    private final HallService hallService;
    private final ShowTimeService showTimeService;
    private final MovieInfoService movieInfoService;
    private final TicketService ticketService;
    private final UserService userService;


    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("cinemas", cinemaService.getAll());
        model.addAttribute("halls", hallService.getAllHalls());
        model.addAttribute("movieInfos", movieInfoService.findAllMovieInfos());
        model.addAttribute("movie", new Movie());
        model.addAttribute("cinema", new Cinema());
        model.addAttribute("hall", new Hall());
//        model.addAttribute("movieInfo", new MovieInfo());
        return "pageForAdmin";
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/movies";
    }


    @PostMapping("/addMovie")
    public String addMovie(@ModelAttribute("movie") Movie movie) {
        if (movie.getMovieInfo() != null) {
            System.out.println("MovieInfo is present: " + movie.getMovieInfo());
            movie.getMovieInfo().setMovie(movie);
        } else {
            System.out.println("MovieInfo is null");
            movie.setMovieInfo(new MovieInfo());
        }
        movieService.save(movie);
        return "redirect:/admin/addEditMovie";
    }


    @PostMapping("/addCinema")
    public String addCinema(@ModelAttribute("cinema") Cinema cinema, RedirectAttributes redirectAttributes) {
        cinemaService.save(cinema);redirectAttributes.addFlashAttribute("success", "Кинотеатр успешно добавлен!");
        return "redirect:/admin";
    }

    @GetMapping("/editMovie")
    public String showEditMovieForm(@RequestParam("movieId") Long movieId, Model model) {
        Movie movie = movieService.findMovieById(movieId);
        if (movie == null) {
            throw new IllegalArgumentException("Фильм не найден: " + movieId);
        }
        model.addAttribute("movie", movie);
        return "editMovie";
    }

    @PostMapping("/updateMovie")
    public String updateMovie(@ModelAttribute("movie") Movie movie) {
        movieService.update(movie);
        return "redirect:/admin/addEditMovie";
    }

    @PostMapping("/addHall")
    public String addHall(@RequestParam String name, @RequestParam int countOfSeats, @RequestParam Long cinemaId, RedirectAttributes redirectAttributes) {
        Cinema cinema = cinemaService.getCinemaById(cinemaId);
        Hall hall = new Hall();
        hall.setName(name);
        hall.setCountOfSeats(countOfSeats);
        hall.setCinema(cinema);
        hallService.saveHall(hall);
        redirectAttributes.addFlashAttribute("success", "Зал успешно добавлен!");

        return "redirect:/admin";
    }


    @GetMapping("/getHallsByCinema/{cinemaId}")
    @ResponseBody
    public List<Hall> getHallsByCinema(@PathVariable Long cinemaId, Model model) {
        List<Hall> halls = hallService.findByCinemaId(cinemaId);
        model.addAttribute("halls", halls);
        System.out.println("Halls target~~~~~ " + halls);
        System.out.println("Returned halls: " + halls.size());
        return halls;
    }

    @PostMapping("/deleteMovie")
    public String deleteMovie(@RequestParam("movieId") Long movieId) {
        if (!movieService.existsById(movieId)) {
            throw new IllegalArgumentException("Movie not found: " + movieId);
        }
        movieService.deleteById(movieId);
        return "redirect:/admin/addEditMovie";
    }

    @PostMapping("/deleteCinema")
    public String deleteCinema(@RequestParam("cinemaId") Long cinemaId, RedirectAttributes redirectAttributes) {
        cinemaService.deleteById(cinemaId);redirectAttributes.addFlashAttribute("success", "Кинотеатр успешно удален!");
        return "redirect:/admin";
    }

    @PostMapping("/deleteHall")
    public String deleteHall(@RequestParam("hallId") Long hallId, RedirectAttributes redirectAttributes) {
       hallService.deleteHallById(hallId);redirectAttributes.addFlashAttribute("success", "Зал успешно удален!");
        return "redirect:/admin";
    }

    @PostMapping("/deleteShowTime")
    public String deleteShowTime(@RequestParam Long showTimeId) {
        ShowTime showTime = showTimeService.findShowTimeById(showTimeId);
        if (showTime != null) {
            showTimeService.deleteShowTimeById(showTime.getId());
        }
        return "redirect:/admin/showTimePageAdmin";
    }

    @GetMapping("/editShowTime")
    public String editShowTime(@RequestParam Long showTimeId, Model model) {
        ShowTime showTime = showTimeService.findShowTimeById(showTimeId);

        if (showTime != null) {
            model.addAttribute("showTime", showTime);
            model.addAttribute("movies", movieService.getAllMovies());
            model.addAttribute("halls", hallService.getAllHalls());

            return "editShowTime";
        }
        return "redirect:/admin/showTimePageAdmin";
    }


    @GetMapping("/addEditMovie")
    public String showAddEditMoviePage(Model model) {
        List<MovieInfo> movieInfos = movieInfoService.findAllMovieInfos();
        model.addAttribute("movieInfos", movieInfos);

        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("movie", new Movie());
        return "addEditMovie";
    }

    @GetMapping("/showTimePageAdmin")
    public String showShowTimeForAdmin(Model model) {
        List<ShowTime> showTimes = showTimeService.getAllShowTimes();
        model.addAttribute("showTimes", showTimes);
        model.addAttribute("showTime", new ShowTime());
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        List<Hall> halls = hallService.getAllHalls();
        model.addAttribute("halls", halls);
        return "showTimePageAdmin";
    }

//    @PostMapping("/addShowTime")
//    public String addShowTime(@RequestParam Long movieId,
//                              @RequestParam Long hallId,
//                              @RequestParam String startTime,
//                              @RequestParam double price) {
//
//        Movie movie = movieService.findMovieById(movieId);
//        Hall hall = hallService.findHallById(hallId);
//        Cinema cinema = hall.getCinema();
//
//        if (!cinema.getMovies().contains(movie)) {
//            cinema.getMovies().add(movie);
//            cinemaService.save(cinema);
//        }
//
//        ShowTime existingShowTime = showTimeService.findByMovieAndHallAndStartTime(movie, hall, Time.valueOf(LocalTime.parse(startTime)));
//        if (existingShowTime != null) {
//            return "redirect:/admin/showTimePageAdmin?error=duplicateShowTime";
//        }
//
//        ShowTime showTime = new ShowTime();
//        showTime.setStartTime(Time.valueOf(LocalTime.parse(startTime)));
//        showTime.setPrice(price);
//        showTime.setMovie(movie);
//        showTime.setHall(hall);
//
//        showTimeService.saveShowTime(showTime);
//        return "redirect:/admin/showTimePageAdmin";
//    }

    @PostMapping("/addShowTime")
    public String addShowTime(@RequestParam Long movieId,
                              @RequestParam Long hallId,
                              @RequestParam String startTime,
                              @RequestParam double price,
                              Model model) {

        ShowTime showTime = showTimeService.prepareShowTimeData(movieId, hallId, startTime, price);


        boolean isTimeSlotTaken = showTimeService.isTimeSlotOccupied(
                hallId,
                Time.valueOf(LocalTime.parse(startTime)),
                showTime.getMovie().getDuration()
        );

        if (isTimeSlotTaken) {
            model.addAttribute("warning", "Сеанс на это время уже занят. Выберите другое время.");
        } else {

            showTimeService.saveShowTime(showTime);
            model.addAttribute("success", "Сеанс успешно добавлен.");
        }

        model.addAttribute("showTimes", showTimeService.getAllShowTimes());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("halls", hallService.getAllHalls());
        return "showTimePageAdmin";
    }


}
