package peaksoft.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Card;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.services.CardService;
import peaksoft.services.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CardService cardService;


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
    public String showSignInForm() {
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

            if (authenticatedUser.getRole() == Role.ADMIN) {
                return "redirect:/admin";
            } else if (authenticatedUser.getRole() == Role.USER) {
                return "redirect:/movies";
            }
            return "redirect:/errorPage";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "signIn";
        }
    }

    @PostMapping("/saveCard")
    public String signup(@RequestParam("userId") Long userId, @ModelAttribute("card") Card card) {
        User user = userService.findById(userId);
        user.setCard(card);
        card.setUser(user);

        cardService.saveCard(card);
        return "redirect:/users/success";
    }
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/signIn";
        }
        model.addAttribute("user", user);
        return "profile";
    }
    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("user", currentUser);
        return "index"; 
    }

}
