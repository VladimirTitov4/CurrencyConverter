package ru.titov.smartsoft.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.titov.smartsoft.entity.ConvertedCurrency;
import ru.titov.smartsoft.entity.User;
import ru.titov.smartsoft.service.CurrencyService;
import ru.titov.smartsoft.util.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public String getCurrencies(@AuthenticationPrincipal User user, Model model) throws Exception {
//        currencyService.getXmlAndSaveToDb(user);
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        model.addAttribute("history", currencyService.loadHistory(user));
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return "currencies";
    }

    @PostMapping
    public String saveConversion(
            @AuthenticationPrincipal User user,
            @RequestParam("currency1") String currency1,
            @RequestParam("currency2") String currency2,
            @RequestParam("firstValue") String firstValue,
            @RequestParam(value = "result", required = false) String result,
            Model model
    ) {
        ConvertedCurrency cc = Converter.getConvertedCurrencyEntity(user, currency1, currency2, firstValue, result);
        currencyService.saveConversion(cc);
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        model.addAttribute("history", currencyService.loadHistory(user));
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return "currencies";
    }

    @PostMapping("/filter")
    public String filterHistory(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "searchDate", required = false) String date,
            @RequestParam(value = "currencyFromHistory1", required = false) String cfh1,
            @RequestParam(value = "currencyFromHistory2", required = false) String cfh2,
            ModelMap model
    ) {
        model.addAttribute("currencies", currencyService.loadRecentCurrencies());
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (!date.trim().isEmpty() && cfh1.isEmpty() && cfh2.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date, dtf);
            model.addAttribute("history",
                    currencyService.loadHistoryByDate(user, localDate));
        } else if (!date.trim().isEmpty() && !cfh1.isEmpty() && cfh2.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date, dtf);
            model.addAttribute("history",
                    currencyService.loadHistoryByDateAndFirstCurrency(user, localDate, cfh1));
        } else if (!date.trim().isEmpty() && cfh1.isEmpty() && !cfh2.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date, dtf);
            model.addAttribute("history",
                    currencyService.loadHistoryByDateAndSecondCurrency(user, localDate, cfh2));
        } else if (!date.trim().isEmpty() && !cfh1.isEmpty() && !cfh2.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date, dtf);
            model.addAttribute("history",
                    currencyService.loadHistoryByAllParams(user, localDate, cfh1, cfh2));
        } else if (date.trim().isEmpty() && !cfh1.isEmpty() && cfh2.isEmpty()) {
            model.addAttribute("history",
                    currencyService.loadHistoryByFirstCurrency(user, cfh1));
        } else if (date.trim().isEmpty() && cfh1.isEmpty() && !cfh2.isEmpty()) {
            model.addAttribute("history",
                    currencyService.loadHistoryBySecondCurrency(user, cfh2));
        } else if (date.trim().isEmpty() && !cfh1.isEmpty() && !cfh2.isEmpty()) {
            model.addAttribute("history",
                    currencyService.loadHistoryByFirstCurrencyAndSecondCurrency(user, cfh1, cfh2));
        } else {
            model.addAttribute("history",
                    currencyService.loadHistory(user));
        }

        return "currencies";
    }
}
