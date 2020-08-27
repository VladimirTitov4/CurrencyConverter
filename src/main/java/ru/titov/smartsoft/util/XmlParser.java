package ru.titov.smartsoft.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.experimental.UtilityClass;
import ru.titov.smartsoft.dto.Valcurs;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@UtilityClass
public class XmlParser {

    public Valcurs getValcurs() throws IOException, XMLStreamException {
        InputStream xmlResource = new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(xmlResource);
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(xmlStreamReader, Valcurs.class);
    }
}
