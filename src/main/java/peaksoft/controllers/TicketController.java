package peaksoft.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import peaksoft.entity.Ticket;
import peaksoft.entity.User;
import peaksoft.services.TicketService;
import peaksoft.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;



    @GetMapping("/selectTicket/{showTimeId}")
    public String selectTicket(@PathVariable Long showTimeId, Model model, HttpSession session) {

        session.setAttribute("showTimeId", showTimeId);
        List<Ticket> availableTickets = ticketService.getAvailableTicketsForShowTime(showTimeId);
//        model.addAttribute("userId", user.getId());
        model.addAttribute("showTimeId", showTimeId);
        model.addAttribute("availableTickets", availableTickets);
             return "ticketManagement";
    }

    @PostMapping("/purchaseTickets")
    public String purchaseTicket(@RequestParam List<Long> ticketIds, HttpSession session, RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/getSignIn";
        }

        boolean allPurchased = ticketService.purchaseTickets(ticketIds, currentUser);

        if (allPurchased) {
            redirectAttributes.addFlashAttribute("message", "Билеты успешно куплены!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Не удалось купить все билеты. Попробуйте снова.");
        }

        return "redirect:/tickets/selectTicket/" + session.getAttribute("showTimeId");
    }


    @GetMapping("/successPage")
    public String successPage() {
        return "successPage";
    }

    @GetMapping("/errorPage")
    public String errorPage() {
        return "errorPage";
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