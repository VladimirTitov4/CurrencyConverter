package ru.titov.smartsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.titov.smartsoft.entity.ConvertedCurrency;

public interface ConvertedCurrencyRepository extends JpaRepository<ConvertedCurrency, Long>, JpaSpecificationExecutor<ConvertedCurrency> {
}
