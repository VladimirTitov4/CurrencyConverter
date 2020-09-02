package ru.titov.smartsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.titov.smartsoft.entity.ConvertedCurrency;
import ru.titov.smartsoft.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface ConvertedCurrencyRepository extends JpaRepository<ConvertedCurrency, Long> {
    List<ConvertedCurrency> findAllByUser(@AuthenticationPrincipal User user);

    List<ConvertedCurrency> findAllByUserAndCreatedAt(@AuthenticationPrincipal User user, LocalDate date);

    @Query(value = "SELECT * FROM converted_currency WHERE user_fid = ?1 and created_at = ?2 and substr(first_currency, 1, 3) = ?3", nativeQuery = true)
    List<ConvertedCurrency> findAllByUserAndCreatedAtAndFirstCurrency(@AuthenticationPrincipal User user, LocalDate date, String firstCurrency);

    @Query(value = "SELECT * FROM converted_currency WHERE user_fid = ?1 and created_at = ?2 and substr(second_currency, 1, 3) = ?3", nativeQuery = true)
    List<ConvertedCurrency> findAllByUserAndCreatedAtAndSecondCurrency(@AuthenticationPrincipal User user, LocalDate date, String secondCurrency);

    @Query(value = "SELECT * FROM converted_currency WHERE user_fid = ?1 and substr(first_currency, 1, 3) = ?2", nativeQuery = true)
    List<ConvertedCurrency> findAllByUserAndFirstCurrency(@AuthenticationPrincipal User user, String firstCurrency);

    @Query(value = "SELECT * FROM converted_currency WHERE user_fid = ?1 and substr(second_currency, 1, 3) = ?2", nativeQuery = true)
    List<ConvertedCurrency> findAllByUserAndSecondCurrency(@AuthenticationPrincipal User user, String secondCurrency);

    @Query(value = "SELECT * FROM converted_currency WHERE user_fid = ?1 and substr(first_currency, 1, 3) = ?2 and substr(second_currency, 1, 3) = ?3", nativeQuery = true)
    List<ConvertedCurrency> findAllByUserAndFirstCurrencyAndSecondCurrency(
            @AuthenticationPrincipal User user,
            String firstCurrency,
            String secondCurrency);

    @Query(value = "SELECT * FROM converted_currency WHERE user_fid = ?1 and created_at = ?2 and substr(first_currency, 1, 3) = ?3 and substr(second_currency, 1, 3) = ?4", nativeQuery = true)
    List<ConvertedCurrency> findAllByUserAndCreatedAtAndFirstCurrencyAndSecondCurrency(
            @AuthenticationPrincipal User user,
            LocalDate date,
            String firstCurrency,
            String secondCurrency);
}
