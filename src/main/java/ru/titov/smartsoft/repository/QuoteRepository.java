package ru.titov.smartsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.titov.smartsoft.entity.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Quote findFirstByOrderByIdDesc();
}
