package ru.titov.smartsoft.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.titov.smartsoft.entity.ConvertedCurrency;
import ru.titov.smartsoft.entity.User;

import java.time.LocalDate;

public class ConvertedCurrencySpecification {

    public static Specification<ConvertedCurrency> hasCreatedAt(LocalDate date) {
        return (r, cq, cb) -> cb.equal(r.get("createdAt"), date);
    }

    public static Specification<ConvertedCurrency> hasFirstCurrency(String firstCurrency) {
        return (r, cq, cb) -> cb.like(r.get("firstCurrency"), firstCurrency + "%");
    }

    public static Specification<ConvertedCurrency> hasSecondCurrency(String secondCurrency) {
        return (r, cq, cb) -> cb.like(r.get("secondCurrency"), secondCurrency + "%");
    }

    public static Specification<ConvertedCurrency> hasUser(@AuthenticationPrincipal User user) {
        return (r, cq, cb) -> cb.equal(r.get("user"), user);
    }
}
