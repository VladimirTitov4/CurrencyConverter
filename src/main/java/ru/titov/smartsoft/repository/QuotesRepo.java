package ru.titov.smartsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.titov.smartsoft.domain.Valute;

public interface QuotesRepo extends JpaRepository<Valute, Long> {

}
