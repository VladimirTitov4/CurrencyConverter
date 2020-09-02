package ru.titov.smartsoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import ru.titov.smartsoft.dto.ValcursDto;
import ru.titov.smartsoft.dto.ValuteDto;
import ru.titov.smartsoft.entity.ConvertedCurrency;
import ru.titov.smartsoft.entity.Currency;
import ru.titov.smartsoft.entity.Quote;
import ru.titov.smartsoft.entity.User;
import ru.titov.smartsoft.feign.CurrencyFeignClient;
import ru.titov.smartsoft.repository.ConvertedCurrencyRepository;
import ru.titov.smartsoft.repository.CurrencyRepository;
import ru.titov.smartsoft.repository.QuoteRepository;
import ru.titov.smartsoft.util.Converter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final QuoteRepository quoteRepository;
    private final ConvertedCurrencyRepository convertedCurrencyRepository;
    private final CurrencyFeignClient currencyFeignClient;

    public void getXmlAndSaveToDb(@AuthenticationPrincipal User user) throws Exception {
        ValcursDto valcurs = currencyFeignClient.getXmlDaily();
        if (!checkIsUpToDate(valcurs)) {
            Quote quote = new Quote();
            quote.setName(valcurs.getName());
            quote.setDate(valcurs.getDate());
            quoteRepository.save(quote);
            for (ValuteDto valute : valcurs.getValutes()) {
                currencyRepository.save(Converter.toCurrencyEntity(valute, quote, user));
            }
        }
    }

    public boolean checkIsUpToDate(ValcursDto valcurs) {
        Quote lastQuote = quoteRepository.findFirstByOrderByIdDesc();
        if (lastQuote == null) {
            System.out.println("Found nothing, saving quotes for the first time");
            return false;
        }
        LocalDate DBDate = LocalDate.parse(lastQuote.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate CBRDate = LocalDate.parse(valcurs.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        if (DBDate.equals(CBRDate)) {
            System.out.println("Date is up to Date");
            return true;
        }
        if (DBDate.isBefore(CBRDate)) {
            System.out.println("Date is expired, saving new quotes");
            return false;
        }
        if (DBDate.getDayOfWeek() == DayOfWeek.MONDAY
                && CBRDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            System.out.println("Today is Monday and current quotes are good");
            return true;
        }
        return false;
    }

    public List<Currency> loadRecentCurrencies() {
        Long lastId = quoteRepository.findFirstByOrderByIdDesc().getId();
        return currencyRepository.findAllByQuote_Id(lastId);
    }

    public void saveConversion(ConvertedCurrency convertedCurrency) {
        convertedCurrencyRepository.save(convertedCurrency);
    }

    public List<ConvertedCurrency> loadHistory(User user) {
        return convertedCurrencyRepository.findAllByUser(user);
    }

    public List<ConvertedCurrency> loadHistoryByDate(User user, LocalDate localdate) {
        return convertedCurrencyRepository.findAllByUserAndCreatedAt(user, localdate);
    }

    public List<ConvertedCurrency> loadHistoryByDateAndFirstCurrency(User user, LocalDate localdate, String firstCurrency) {
        return convertedCurrencyRepository.findAllByUserAndCreatedAtAndFirstCurrency(user, localdate, firstCurrency);
    }

    public List<ConvertedCurrency> loadHistoryByDateAndSecondCurrency(User user, LocalDate localdate, String secondCurrency) {
        return convertedCurrencyRepository.findAllByUserAndCreatedAtAndSecondCurrency(user, localdate, secondCurrency);
    }

    public List<ConvertedCurrency> loadHistoryByAllParams(User user, LocalDate localdate, String firstCurrency, String secondCurrency) {
        return convertedCurrencyRepository.findAllByUserAndCreatedAtAndFirstCurrencyAndSecondCurrency(
                user, localdate, firstCurrency, secondCurrency);
    }

    public List<ConvertedCurrency> loadHistoryByFirstCurrencyAndSecondCurrency(User user, String firstCurrency, String secondCurrency) {
        return convertedCurrencyRepository.findAllByUserAndFirstCurrencyAndSecondCurrency(
                user, firstCurrency, secondCurrency);
    }

    public List<ConvertedCurrency> loadHistoryByFirstCurrency(User user, String firstCurrency) {
        return convertedCurrencyRepository.findAllByUserAndFirstCurrency(user, firstCurrency);
    }

    public List<ConvertedCurrency> loadHistoryBySecondCurrency(User user, String secondCurrency) {
        return convertedCurrencyRepository.findAllByUserAndSecondCurrency(user, secondCurrency);
    }
}
