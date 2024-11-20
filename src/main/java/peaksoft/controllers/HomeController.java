package peaksoft.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/h")
public class HomeController {

    @GetMapping("/homePage")
    public String index() {
        return "homePage";
    }

}
