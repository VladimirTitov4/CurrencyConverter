package ru.titov.smartsoft.config;

import feign.Feign;
import feign.Logger;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.jaxb.JAXBEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.titov.smartsoft.feign.CurrencyFeignClient;

import java.nio.charset.StandardCharsets;

@Configuration
public class FeignConfig {

    @Value("http://www.cbr.ru/scripts")
    private String url;

    @Bean
    public JAXBContextFactory jaxbFactory() {
        return new JAXBContextFactory.Builder()
                .withMarshallerFormattedOutput(true)
                .withMarshallerJAXBEncoding(StandardCharsets.UTF_8.name())
                .build();
    }

    @Bean
    public CurrencyFeignClient currencyFeignClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JAXBEncoder(jaxbFactory()))
                .decoder(new JAXBDecoder(jaxbFactory()))
                .logger(new Slf4jLogger(CurrencyFeignClient.class))
                .logLevel(Logger.Level.FULL)
                .target(CurrencyFeignClient.class, url);
    }
}