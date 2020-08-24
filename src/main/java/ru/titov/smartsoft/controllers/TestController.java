package ru.titov.smartsoft.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String getTest() {

        return "test";
    }

    @PostMapping
    public String postTest(
            @RequestParam String firstValue,
            @RequestParam String secondValue,
            Model model
    ) {
        model.addAttribute("firstValue", firstValue);
        model.addAttribute("secondValue", secondValue);

        return "test";
    }
}
