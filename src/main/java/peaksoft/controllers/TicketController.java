package peaksoft.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import peaksoft.entity.ShowTime;
import peaksoft.entity.Ticket;
import peaksoft.entity.User;
import peaksoft.services.ShowTimeService;
import peaksoft.services.TicketService;
import peaksoft.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;
    private final ShowTimeService showTimeService;



@GetMapping("/selectTicket/{showTimeId}")
public String selectTicket(@PathVariable Long showTimeId, Model model, HttpSession session) {
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        return "redirect:/users/getSignIn";
    }

    List<Ticket> availableTickets = ticketService.getAvailableTicketsForShowTime(showTimeId);

    ShowTime showTime = showTimeService.findShowTimeById(showTimeId);
    session.setAttribute("showTimeId", showTimeId);

    model.addAttribute("showTimeId", showTimeId);
    model.addAttribute("availableTickets", availableTickets);
    model.addAttribute("currentBalance", currentUser.getCard().getBalance());
    model.addAttribute("showTimePrice", showTime.getPrice());

    return "ticketManagement";
}

    @PostMapping("/purchaseTickets")
    public String purchaseTicket(@RequestParam List<Long> ticketIds, HttpSession session, RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/getSignIn";
        }
        if (ticketIds == null || ticketIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Вы ничего не выбрали.");
            return "redirect:/tickets/selectTicket/" + session.getAttribute("showTimeId");
        }
        Long showTimeId = (Long) session.getAttribute("showTimeId");
        if (showTimeId == null) {
            redirectAttributes.addFlashAttribute("message", "Ошибка: не найден сеанс.");
            return "redirect:/tickets/selectTicket";
        }

        boolean allPurchased = ticketService.purchaseTickets(ticketIds, currentUser);

        if (allPurchased) {
            redirectAttributes.addFlashAttribute("message", "Билеты успешно куплены!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Не удалось купить все билеты. Попробуйте снова.");
        }

        return "redirect:/tickets/selectTicket/" + session.getAttribute("showTimeId");
    }

    

    @PostMapping("/create")
    public ResponseEntity<String> createTicket(
            @RequestParam Long userId,
            @RequestParam Long showTimeId) {
        ticketService.createTicket(userId, showTimeId);
        return ResponseEntity.ok("Ticket created successfully");
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok("Ticket deleted successfully");
    }

}