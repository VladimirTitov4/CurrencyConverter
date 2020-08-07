package ru.titov.smartsoft.controllers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import ru.titov.smartsoft.dto.Valcurs;
import ru.titov.smartsoft.dto.Valute;
import ru.titov.smartsoft.entity.Currency;
import ru.titov.smartsoft.repository.QuotesRepo;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.net.URL;

@org.springframework.web.bind.annotation.RestController
public class CurrencyController {

    @Autowired
    private QuotesRepo currencyRepo;

    Valcurs getData() throws Exception {
        InputStream xmlResource = new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(xmlResource);

        XmlMapper mapper = new XmlMapper();

        return mapper.readValue(xmlStreamReader, Valcurs.class);
    }

    @GetMapping(value = "/currency")
    public String saveCurrencies() throws Exception {
        Valcurs valcurs = getData();



        for (Valute valute : valcurs.getValute()) {
            Currency currency = new Currency();
            currency.setValuteId(valute.getValuteId());
            currency.setNumCode(valute.getNumCode());
            currency.setCharCode(valute.getCharCode());
            currency.setNominal(valute.getNominal());
            currency.setName(valute.getName());
            currency.setValue(valute.getValue());
            currencyRepo.save(currency);
        }

        return "redirect:/login";
    }
}
