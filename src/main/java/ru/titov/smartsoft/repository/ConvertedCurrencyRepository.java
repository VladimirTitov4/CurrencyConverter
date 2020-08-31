package ru.titov.smartsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.titov.smartsoft.entity.ConvertedCurrency;
import ru.titov.smartsoft.entity.User;

import java.util.List;

public interface ConvertedCurrencyRepository extends JpaRepository<ConvertedCurrency, Long> {
    List<ConvertedCurrency> findAllByUser(@AuthenticationPrincipal User user);
}
