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

//    @GetMapping("/{showTimeId}/seats")
//    public String getSeatsForShowTime(@PathVariable Long showTimeId, Model model) {
//        ShowTime showTime = showTimeService.findShowTimeById(showTimeId);
//        Hall hall = showTime.getHall();
//        List<Seat> seats = seatRepo.findByHallId(hall.getId());
//
//
//        List<Ticket> bookedTickets = ticketRepo.getTicketsByShowTimeId(showTimeId);
//        Set<Long> bookedSeatIds = bookedTickets.stream()
//                .map(ticket -> ticket.getSeat().getId())
//                .collect(Collectors.toSet());
//
//        model.addAttribute("showTime", showTime);
//        model.addAttribute("seats", seats);
//        model.addAttribute("bookedSeatIds", bookedSeatIds);
//        model.addAttribute("users", userService.getAllUsers());
//
//        return "seats";
//    }
//    @PostMapping("/tickets/book")
//    public String bookTickets(@RequestParam("showTimeId") Long showTimeId,
//                              @RequestParam("userId") Long userId,
//                              @RequestParam("seats") List<Long> seatIds,
//                              Model model) {
//        ShowTime showTime = showTimeService.findShowTimeById(showTimeId);
//        User user = userService.findById(userId);
//
//
//        for (Long seatId : seatIds) {
//            Seat seat = seatRepo.findById(seatId);
//            Ticket ticket = new Ticket();
//            ticket.setSeatNumber(seat.getNumber());
//            ticket.setShowTime(showTime);
//            ticket.setUser(user);
//            ticketRepo.saveTicket(ticket);
//        }
//
//        return "redirect:/showtime/" + showTimeId + "/seats";
//    }
}
