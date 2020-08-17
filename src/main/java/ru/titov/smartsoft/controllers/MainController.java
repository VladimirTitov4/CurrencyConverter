package ru.titov.smartsoft.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.titov.smartsoft.entity.User;

@Controller
public class  MainController {

    @GetMapping("/")
    public String home(Model model) {
        return "main";
    }

    @GetMapping("/user-details")
    public String showUserInfo(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("currentUser", user);

        return "user-details";
    }
}
