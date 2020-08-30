package ru.titov.smartsoft.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.titov.smartsoft.entity.ConvertedCurrency;
import ru.titov.smartsoft.entity.Currency;
import ru.titov.smartsoft.entity.User;
import ru.titov.smartsoft.service.CurrencyService;

import java.io.IOException;
import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public String getCurrencies(@AuthenticationPrincipal User user, Model model) throws Exception {
        currencyService.getXmlAndSaveToDb(user);
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        return "currencies";
    }

    @PostMapping
    public String saveConversion(
            @RequestParam("currency1") String currency1,
            @RequestParam("currency2") String currency2,
            @RequestParam("firstValue") String firstValue,
            @RequestParam("result") String result,
            Model model) {
        System.out.println(currency1);
        System.out.println(currency2);
        System.out.println(firstValue);
        System.out.println(result);
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        return "currencies";
    }
}
