package peaksoft.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.services.TicketService;
import peaksoft.services.UserService;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;

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
    @PostMapping("/purchase")
    public String purchaseTicket(@RequestParam Long ticketId, @RequestParam Long userId, Model model) {
        try {
            ticketService.purchaseTicket(ticketId, userService.findById(userId));
            model.addAttribute("message", "Ticket purchased successfully.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/tickets";
    }
}