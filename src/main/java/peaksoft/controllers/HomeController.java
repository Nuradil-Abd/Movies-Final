package peaksoft.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import peaksoft.entity.User;

@Controller
@RequestMapping("/h")
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "index";
    }


}
