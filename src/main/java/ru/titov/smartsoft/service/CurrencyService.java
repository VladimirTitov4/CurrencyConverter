package ru.titov.smartsoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import ru.titov.smartsoft.dto.Valcurs;
import ru.titov.smartsoft.dto.Valute;
import ru.titov.smartsoft.entity.Currency;
import ru.titov.smartsoft.entity.Quote;
import ru.titov.smartsoft.entity.User;
import ru.titov.smartsoft.repository.CurrencyRepository;
import ru.titov.smartsoft.repository.QuoteRepository;
import ru.titov.smartsoft.util.Converter;
import ru.titov.smartsoft.util.XmlParser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final QuoteRepository quoteRepository;

    public void getXmlAndSaveToDb(@AuthenticationPrincipal User user) throws Exception {
        Valcurs valcurs = XmlParser.getValcurs();
        if (!checkIsUpToDate(valcurs)) {
            Quote quote = new Quote();
            quote.setName(valcurs.getName());
            quote.setDate(valcurs.getDate());
            quoteRepository.save(quote);
            
            for (Valute valute : valcurs.getValute()) {
                currencyRepository.save(Converter.toCurrencyEntity(valute, quote, user));
            }
        }
    }

    public boolean checkIsUpToDate(Valcurs valcurs) {
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

    public Currency getCurrencyByCharCode(String charCode) {
        return currencyRepository.findByCharCode(charCode);
    }
}
