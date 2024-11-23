package peaksoft.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import peaksoft.entity.Cinema;
import peaksoft.entity.Hall;
import peaksoft.entity.ShowTime;
import peaksoft.entity.Ticket;
import peaksoft.services.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/delete")

public class DeleteController {
    private final MovieService movieService;
    private final CinemaService cinemaService;
    private final HallService hallService;
    private final ShowTimeService showTimeService;
    private final MovieInfoService movieInfoService;
    private final TicketService ticketService;


    @PostMapping("/deleteCinema")
    public String deleteCinema(@RequestParam("cinemaId") Long cinemaId, RedirectAttributes redirectAttributes) {
        Cinema cinema = cinemaService.getCinemaById(cinemaId);
        boolean hasShowTimes = false;
        for (Hall hall : cinema.getHalls()) {
            if (!hall.getShowTimes().isEmpty()) {
                hasShowTimes = true;
                break;
            }
        }
        if (hasShowTimes) {
            redirectAttributes.addFlashAttribute("confirmationMessage", "У этого кинотеатра есть зал с сеансами. Вы уверены, что хотите его удалить?");
            return "redirect:/delete/confirmDeleteCinema?cinemaId=" + cinemaId;
        } else {
            cinemaService.deleteById(cinemaId);
            redirectAttributes.addFlashAttribute("success", "Кинотеатр успешно удален!");
            return "redirect:/admin";
        }
    }

    @GetMapping("/confirmDeleteCinema")
    public String confirmDeleteCinema(@RequestParam("cinemaId") Long cinemaId, Model model) {
        Cinema cinema = cinemaService.getCinemaById(cinemaId);
        model.addAttribute("cinema", cinema);
        return "confirmDeleteCinema";
    }

    @PostMapping("/confirmDeleteCinema")
    public String confirmDeleteCinemaPost(@RequestParam("cinemaId") Long cinemaId, RedirectAttributes redirectAttributes) {
        cinemaService.deleteById(cinemaId);
        redirectAttributes.addFlashAttribute("success", "Кинотеатр успешно удален!");
        return "redirect:/admin";
    }

    @PostMapping("/deleteHall")
    public String deleteHall(@RequestParam("hallId") Long hallId, RedirectAttributes redirectAttributes) {
        if (showTimeService.hasShowTimesInHall(hallId)) {
            redirectAttributes.addFlashAttribute("warning", "Невозможно удалить зал, так как он имеет связанные сеансы.");
            return "redirect:/admin";
        }
        hallService.deleteHallById(hallId);
        redirectAttributes.addFlashAttribute("success", "Зал успешно удален!");
        return "redirect:/admin";
    }

    @PostMapping("/deleteShowTime")
    public String deleteShowTime(@RequestParam Long showTimeId, RedirectAttributes redirectAttributes) {
        List<Ticket> allTickets = ticketService.getTicketsByShowTimeId(showTimeId);
        boolean hasBoughtTicket = allTickets.stream().anyMatch(Ticket::isPurchased);

        if (hasBoughtTicket) {
            return "redirect:/delete/confirmDeleteShowTime?showTimeId=" + showTimeId;
        }
        showTimeService.deleteShowTimeWithTickets(showTimeId);
        redirectAttributes.addFlashAttribute("success", "Сеанс и все связанные билеты успешно удалены!");
        return "redirect:/admin/showTimePageAdmin";
    }

    @GetMapping("/confirmDeleteShowTime")
    public String confirmDeleteShowTime(@RequestParam Long showTimeId, Model model) {
        model.addAttribute("showTimeId", showTimeId);
        return "confirmDeleteShowTime";
    }
    @PostMapping("/deleteConfirmShowTime")
    public String deleteConfirmShowTime(@RequestParam Long showTimeId, RedirectAttributes redirectAttributes) {
        showTimeService.deleteShowTimeWithTickets(showTimeId);
        redirectAttributes.addFlashAttribute("success", "Сеанс и все связанные билеты успешно удалены!");
        return "redirect:/admin/showTimePageAdmin";
    }
}
