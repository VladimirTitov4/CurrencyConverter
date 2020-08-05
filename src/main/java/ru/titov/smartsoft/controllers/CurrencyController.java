package ru.titov.smartsoft.controllers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.bind.annotation.GetMapping;
import ru.titov.smartsoft.domain.Valcurs;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@org.springframework.web.bind.annotation.RestController
public class CurrencyController {

    @GetMapping(value = "/currency")
    Valcurs valcurs() throws XMLStreamException, IOException {
//        InputStream xmlResource = CurrencyController.class.getClassLoader()
//                .getResourceAsStream("test.xml");

        InputStream xmlResource = new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(xmlResource);

        XmlMapper mapper = new XmlMapper();
        Valcurs valcurs = mapper.readValue(xmlStreamReader, Valcurs.class);

        return valcurs;
    }
}
