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
//        currencyService.getXmlAndSaveToDb(user);
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        return "currencies";
    }

//    @PostMapping("/calculate")
//    @ResponseBody
//    public ConvertedCurrency convert(@RequestBody String jsonStr) throws IOException {
//        System.out.println(jsonStr);
//        byte[] jsonData = jsonStr.getBytes();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(jsonData);
//        JsonNode firstCharCode = jsonNode.path("firstCharCode");
//        JsonNode amountRow = jsonNode.path("amount");
//        JsonNode secondCharCode = jsonNode.path("secondCharCode");
//        int amount = amountRow.asInt();
//        if (amount < 1) amount = 1;
//        Currency firstCurrencyFromDb = currencyService.getCurrencyByCharCode(firstCharCode.asText());
//        Currency secondCurrencyFromDb = currencyService.getCurrencyByCharCode(secondCharCode.asText());
//        return new ConvertedCurrency(
//                new BigDecimal(firstCurrencyFromDb.getValue().replaceAll(",", ".")),
//                new BigDecimal(secondCurrencyFromDb.getValue().replaceAll(",", ".")),
//                new BigDecimal(firstCurrencyFromDb.getNominal()),
//                new BigDecimal(secondCurrencyFromDb.getNominal()),
//                new BigDecimal(amount));
//    }

    @PostMapping("/calculate")
    @ResponseBody
    public String convert(@RequestBody String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        Currency firstCurrencyFromDb = currencyService.getCurrencyByCharCode(jsonObject.get("firstCharCode").toString());
        Currency secondCurrencyFromDb = currencyService.getCurrencyByCharCode(jsonObject.get("secondCharCode").toString());
        JSONObject resultJsonObject = new JSONObject();
        resultJsonObject.put("firstValue", firstCurrencyFromDb.getValue());
        resultJsonObject.put("firstNominal", firstCurrencyFromDb.getNominal());
        resultJsonObject.put("secondValue", secondCurrencyFromDb.getValue());
        resultJsonObject.put("secondNominal", secondCurrencyFromDb.getNominal());
        return resultJsonObject.toString();
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
