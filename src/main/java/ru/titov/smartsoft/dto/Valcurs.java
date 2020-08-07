package ru.titov.smartsoft.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "ValCurs")
public class Valcurs {

    @JacksonXmlProperty(localName = "Date", isAttribute = true)
    private String date;

    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlElementWrapper(localName = "Valute", useWrapping = false)
    @JacksonXmlProperty(localName = "Valute")
    private List<Valute> valute;

    public Valcurs() {
    }

    public Valcurs(String date, String name, List<Valute> valutes) {
        this.date = date;
        this.name = name;
        this.valute = valutes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Valute> getValute() {
        return valute;
    }

    public void setValute(List<Valute> valute) {
        this.valute = valute;
    }

    @Override
    public String toString() {
        return "Valcurs{" +
                "valutes=" + valute +
                '}';
    }
}
