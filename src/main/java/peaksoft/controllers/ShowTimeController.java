package peaksoft.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import peaksoft.repo.TicketRepo;
import peaksoft.services.MovieService;
import peaksoft.services.ShowTimeService;
import peaksoft.services.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/showtime")
public class ShowTimeController {
    private final MovieService movieService;
    private final ShowTimeService showTimeService;

    private final TicketRepo ticketRepo;
    private final UserService userService;


}
