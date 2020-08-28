package ru.titov.smartsoft.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String get() {
        return "test";
    }

    @PostMapping
    public String handleData(
            @RequestParam("country") String country) {
        System.out.println(country);
        return "test";
    }
}
