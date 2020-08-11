package ru.titov.smartsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.titov.smartsoft.dto.Valute;
import ru.titov.smartsoft.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
