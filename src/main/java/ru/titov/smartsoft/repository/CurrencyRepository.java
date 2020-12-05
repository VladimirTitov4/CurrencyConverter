package ru.titov.smartsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.titov.smartsoft.entity.Currency;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findAllByQuote_Id(Long lastId);
}
