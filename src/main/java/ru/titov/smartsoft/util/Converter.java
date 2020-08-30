package ru.titov.smartsoft.util;

import lombok.experimental.UtilityClass;
import ru.titov.smartsoft.dto.ValuteDto;
import ru.titov.smartsoft.entity.Currency;
import ru.titov.smartsoft.entity.Quote;
import ru.titov.smartsoft.entity.User;

@UtilityClass
public class Converter {

    public Currency toCurrencyEntity(ValuteDto valute, Quote quote, User user) {
        Currency currency = new Currency();
        currency.setValuteId(valute.getId());
        currency.setNumCode(valute.getNumCode());
        currency.setCharCode(valute.getCharCode());
        currency.setNominal(valute.getNominal());
        currency.setName(valute.getName());
        currency.setValue(valute.getValue());
        currency.setQuote(quote);
        currency.setUser(user);
        return currency;
    }
}
