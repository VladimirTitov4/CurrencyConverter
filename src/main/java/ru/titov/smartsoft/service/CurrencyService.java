package ru.titov.smartsoft.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final QuoteRepository quoteRepository;

    public void getXmlAndSaveToDb(@AuthenticationPrincipal User user) throws Exception {
        if (!checkIsUpToDate()) {
            InputStream xmlResource = new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(xmlResource);
            XmlMapper mapper = new XmlMapper();
            Valcurs valcurs = mapper.readValue(xmlStreamReader, Valcurs.class);

            Quote quote = new Quote();
            quote.setName(valcurs.getName());
            quote.setDate(valcurs.getDate());
            quoteRepository.save(quote);
            
            for (Valute valute : valcurs.getValute()) {
                Currency currency = new Currency();
                currency.setValuteId(valute.getValuteId());
                currency.setNumCode(valute.getNumCode());
                currency.setCharCode(valute.getCharCode());
                currency.setNominal(valute.getNominal());
                currency.setName(valute.getName());
                currency.setValue(valute.getValue());
                currency.setQuote(quote);
                currency.setUser(user);
                currencyRepository.save(currency);
            }
        }
    }

    public boolean checkIsUpToDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date todayDate = new Date();
        List<Quote> quotes = quoteRepository.findAll();
        if (quotes.isEmpty()) {
            System.out.println("Found nothing, saving Quotes for the first time");
        }
        for (Quote quote : quotes) {
            Date DbDate = sdf.parse(quote.getDate());
            if (todayDate.before(DbDate) || todayDate.equals(DbDate)) {
                System.out.println("Date is up to Date");
                return true;
            } else {
                System.out.println("No suitable Date found, saving new Quotes to DB");
            }
        }
        return false;
    }

    public List<Currency> loadCurrencies() {
        return currencyRepository.findAll();
    }
}
