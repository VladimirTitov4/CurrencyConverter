package ru.titov.smartsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.titov.smartsoft.dto.Valute;
import ru.titov.smartsoft.entity.Currency;

public interface QuotesRepo extends JpaRepository<Currency, Long> {

}
