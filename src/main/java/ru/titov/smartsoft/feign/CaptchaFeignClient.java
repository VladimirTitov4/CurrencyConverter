package ru.titov.smartsoft.feign;

import feign.Param;
import feign.RequestLine;
import ru.titov.smartsoft.dto.CaptchaResponseDto;

public interface CaptchaFeignClient {

    @RequestLine("POST /siteverify?secret={secret}&response={response}")
    CaptchaResponseDto getCaptchaResponse(@Param("secret") String secret, @Param("response") String response);
}

