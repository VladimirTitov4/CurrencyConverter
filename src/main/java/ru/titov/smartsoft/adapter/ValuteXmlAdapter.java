//package ru.titov.smartsoft.adapter;
//
//import javax.xml.bind.annotation.adapters.XmlAdapter;
//
//public class ValuteXmlAdapter extends XmlAdapter<String, Double> {
//
//    @Override
//    public Double unmarshal(String v) {
//        String replacedString = v.replaceAll(",", ".");
//        return Double.parseDouble(replacedString);
//    }
//
//    @Override
//    public String marshal(Double v) {
//        return String.valueOf(v);
//    }
//}