package ru.titov.smartsoft.config;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.jaxb.JAXBEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.titov.smartsoft.feign.CaptchaFeignClient;
import ru.titov.smartsoft.feign.CurrencyFeignClient;

import java.nio.charset.StandardCharsets;

@Configuration
public class FeignConfig {

    @Value("http://www.cbr.ru/scripts")
    private String url;

    @Value("https://www.google.com/recaptcha/api")
    private String captchaUrl;

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
                .target(CurrencyFeignClient.class, url);
    }

    @Bean
    public CaptchaFeignClient captchaFeignClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CaptchaFeignClient.class, captchaUrl);
    }
}