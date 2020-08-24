package ru.titov.smartsoft.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.titov.smartsoft.entity.ConvertCurrency;
import ru.titov.smartsoft.entity.User;
import ru.titov.smartsoft.service.CurrencyService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public String saveCurrencies(
            @AuthenticationPrincipal User user,
            Model model
    ) throws Exception {
        currencyService.getXmlAndSaveToDb(user);

        model.addAttribute("currencies", currencyService.loadCurrencies());

        return "currencies";
    }

    @PostMapping
    public String convertCurrencies(
            @RequestParam String firstValue,
            @RequestParam String secondValue,
            Model model
    ) {

        model.addAttribute("currencies", currencyService.loadCurrencies());

        return "currencies";
    }
}
