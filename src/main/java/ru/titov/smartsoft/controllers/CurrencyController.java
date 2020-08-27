package ru.titov.smartsoft.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
    public String saveCurrencies(@AuthenticationPrincipal User user, Model model) throws Exception {
//        currencyService.getXmlAndSaveToDb(user);
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        return "currencies";
    }

    @PostMapping("/test")
    @ResponseBody
    public ConvertedCurrency test(@RequestBody String jsonStr) throws IOException {
        byte[] jsonData = jsonStr.getBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonData);
        JsonNode firstCharCode = jsonNode.path("firstCharCode");
        JsonNode amountRow = jsonNode.path("amount");
        JsonNode secondCharCode = jsonNode.path("secondCharCode");
        int amount = amountRow.asInt();
        if (amount < 1) amount = 1;
        Currency firstCurrencyFromDb = currencyService.getCurrencyByCharCode(firstCharCode.asText());
        Currency secondCurrencyFromDb = currencyService.getCurrencyByCharCode(secondCharCode.asText());
        return new ConvertedCurrency(
                new BigDecimal(firstCurrencyFromDb.getValue().replaceAll(",", ".")),
                new BigDecimal(secondCurrencyFromDb.getValue().replaceAll(",", ".")),
                new BigDecimal(firstCurrencyFromDb.getNominal()),
                new BigDecimal(secondCurrencyFromDb.getNominal()),
                new BigDecimal(amount));
    }

    @PostMapping
    public String convertCurrencies(Model model) {
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        System.out.println("form");
        return "currencies";
    }
}
