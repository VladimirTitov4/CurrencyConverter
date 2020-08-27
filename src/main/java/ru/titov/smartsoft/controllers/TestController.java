package ru.titov.smartsoft.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/currency/{code}")
    public void get(@PathVariable String code) {
        System.out.println(code);
    }


}
