package ru.titov.smartsoft.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.titov.smartsoft.entity.Currency;
import ru.titov.smartsoft.entity.ResponseTransfer;
import ru.titov.smartsoft.entity.User;
import ru.titov.smartsoft.service.CurrencyService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public String saveCurrencies(@AuthenticationPrincipal User user, Model model) throws Exception {
        currencyService.getXmlAndSaveToDb(user);
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        return "currencies";
    }

    @PostMapping("/test")
    @ResponseBody
    public ResponseTransfer test(@RequestBody String charCode) throws JSONException {
        JSONObject jsonObject = new JSONObject(charCode);
        Currency currency = currencyService.getCurrencyByCharCode(jsonObject.getString("charCode"));
        System.out.println(currency.getValue());
        return new ResponseTransfer("Hello!");
    }

    @PostMapping
    public String convertCurrencies(Model model) {
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        return "currencies";
    }
}
