package ru.titov.smartsoft.util;

import lombok.experimental.UtilityClass;
import ru.titov.smartsoft.dto.ValuteDto;
import ru.titov.smartsoft.entity.ConvertedCurrency;
import ru.titov.smartsoft.entity.Currency;
import ru.titov.smartsoft.entity.Quote;
import ru.titov.smartsoft.entity.User;

@UtilityClass
public class ConverterUtil {

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

    public ConvertedCurrency getConvertedCurrencyEntity(User user, String currencyOne, String currencyTwo, String amount, String result) {
        if (amount.isEmpty()) amount = "1";
        if (result.isEmpty()) result = "1";
        ConvertedCurrency convertedCurrency = new ConvertedCurrency();
        convertedCurrency.setFirstCurrency(currencyOne.substring(0, currencyOne.indexOf('|')-1));
        convertedCurrency.setSecondCurrency(currencyTwo.substring(0, currencyTwo.indexOf('|')-1));
        convertedCurrency.setAmountToConvert(amount);
        convertedCurrency.setResult(result);
        convertedCurrency.setUser(user);
        return convertedCurrency;
    }
}
