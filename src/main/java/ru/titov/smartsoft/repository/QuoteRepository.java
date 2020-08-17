package ru.titov.smartsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.titov.smartsoft.entity.Quote;

import java.time.LocalDate;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findAllByDate(String date);
}
