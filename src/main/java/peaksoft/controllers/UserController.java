package peaksoft.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Card;
import peaksoft.entity.Ticket;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.services.CardService;
import peaksoft.services.TicketService;
import peaksoft.services.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CardService cardService;
    private final TicketService ticketService;

    @GetMapping("/getSignup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/postSignup")
    public String signup(@ModelAttribute("user") User user, Model model) {
        userService.registerUser(user);
        model.addAttribute("card", new Card());
        model.addAttribute("userId", user.getId());
        return "enterCardDetails";
    }

    @GetMapping("/getSignIn")
    public String showSignInForm(HttpServletRequest request, HttpSession session) {
        String referer = request.getHeader("Referer");
        if (referer == null) {
            referer = request.getRequestURL().toString();
        }
        session.setAttribute("redirectUrl", referer);
        return "signIn";
    }

    @GetMapping("/success")
    public String showLoginForm() {
        return "signup-success";
    }
    
    @PostMapping("/postSignIn")
    public String signIn(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        User authenticatedUser = userService.authenticate(email, password);
        if (authenticatedUser != null) {
            session.setAttribute("currentUser", authenticatedUser);
            String redirectUrl = (String) session.getAttribute("redirectUrl");

            if (authenticatedUser.getRole() == Role.ADMIN) {
                return "redirect:/admin";
            }
            else if (authenticatedUser.getRole() == Role.USER) {
                if (redirectUrl != null) {
                    return "redirect:" + redirectUrl;
                } else {
                    return "redirect:/movies";
                }
            }
            return "redirect:/errorPage";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "signIn";
        }
    }

    @PostMapping("/saveCard")
    public String signup(@RequestParam("userId") Long userId, @ModelAttribute("card") Card card,HttpSession session) {
        User user = userService.findById(userId);
        session.setAttribute("currentUser", user);
        user.setCard(card);
        card.setUser(user);

        cardService.saveCard(card);
        return "redirect:/movies";
    }
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/signIn";
        }
        model.addAttribute("user", user);
        List<Ticket> purchasedTickets = ticketService.getTicketsByUserId(user.getId());
        model.addAttribute("purchasedTickets", purchasedTickets);
        return "profile";
    }
    
    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("user", currentUser);
        return "index";
    }

}
