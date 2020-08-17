package ru.titov.smartsoft.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.titov.smartsoft.entity.User;
import ru.titov.smartsoft.service.CurrencyService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public String saveCurrencies(@AuthenticationPrincipal User user) throws Exception {
        currencyService.getXmlAndSaveToDb(user);

        return "SUCCESS";
    }
}
