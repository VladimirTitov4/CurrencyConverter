package ru.titov.smartsoft.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.titov.smartsoft.entity.ConvertedCurrency;
import ru.titov.smartsoft.entity.User;
import ru.titov.smartsoft.service.CurrencyService;
import ru.titov.smartsoft.service.HistoryFilterService;
import ru.titov.smartsoft.util.ConverterUtil;

import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final HistoryFilterService filterService;

    @GetMapping
    public String getCurrencies(@AuthenticationPrincipal User user, Model model) throws Exception {
        currencyService.getXmlAndSaveToDb(user);
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        model.addAttribute("history", filterService.loadHistory(user));
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return "currencies";
    }

    @PostMapping
    public String saveConversion(
            @AuthenticationPrincipal User user,
            @RequestParam("currency1") String currencyOne,
            @RequestParam("currency2") String currencyTwo,
            @RequestParam("firstValue") String firstValue,
            @RequestParam(value = "result", required = false) String result,
            Model model
    ) {
        ConvertedCurrency cc = ConverterUtil.getConvertedCurrencyEntity(user, currencyOne, currencyTwo, firstValue, result);
        currencyService.saveConversion(cc);
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        model.addAttribute("history", filterService.loadHistory(user));
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return "currencies";
    }

    @PostMapping("/filter")
    public String filterHistory(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "searchDate", required = false) String date,
            @RequestParam(value = "currencyFromHistory1", required = false) String cfhOne,
            @RequestParam(value = "currencyFromHistory2", required = false) String cfhTwo,
            ModelMap model
    ) {
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        model.addAttribute("history", filterService.loadFilteredHistory(user, date, cfhOne, cfhTwo));

        return "currencies";
    }
}
