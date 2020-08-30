package ru.titov.smartsoft.feign;

import feign.RequestLine;
import ru.titov.smartsoft.dto.ValcursDto;

public interface CurrencyFeignClient {

    @RequestLine("GET /XML_daily.asp")
    ValcursDto getXmlDaily();
}