package ru.titov.smartsoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.titov.smartsoft.entity.ConvertedCurrency;
import ru.titov.smartsoft.entity.User;
import ru.titov.smartsoft.repository.ConvertedCurrencyRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;
import static ru.titov.smartsoft.specification.ConvertedCurrencySpecification.*;

@Service
@RequiredArgsConstructor
public class HistoryFilterService {

    private final ConvertedCurrencyRepository convertedCurrencyRepository;

    public List<ConvertedCurrency> loadFilteredHistory(User user, String date, String cfhOne, String cfhTwo) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (!date.trim().isEmpty() && cfhOne.isEmpty() && cfhTwo.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date, dtf);
            return loadHistoryByDate(user, localDate);
        } else if (!date.trim().isEmpty() && !cfhOne.isEmpty() && cfhTwo.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date, dtf);
            return loadHistoryByDateAndFirstCurrency(user, localDate, cfhOne);
        } else if (!date.trim().isEmpty() && cfhOne.isEmpty() && !cfhTwo.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date, dtf);
            return loadHistoryByDateAndSecondCurrency(user, localDate, cfhTwo);
        } else if (!date.trim().isEmpty() && !cfhOne.isEmpty() && !cfhTwo.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date, dtf);
            return loadHistoryByAllParams(user, localDate, cfhOne, cfhTwo);
        } else if (date.trim().isEmpty() && !cfhOne.isEmpty() && cfhTwo.isEmpty()) {
            return loadHistoryByFirstCurrency(user, cfhOne);
        } else if (date.trim().isEmpty() && cfhOne.isEmpty() && !cfhTwo.isEmpty()) {
            return loadHistoryBySecondCurrency(user, cfhTwo);
        } else if (date.trim().isEmpty() && !cfhOne.isEmpty() && !cfhTwo.isEmpty()) {
            return loadHistoryByFirstCurrencyAndSecondCurrency(user, cfhOne, cfhTwo);
        } else {
            return loadHistory(user);
        }
    }

    public List<ConvertedCurrency> loadHistory(User user) {
        return convertedCurrencyRepository.findAll(where(hasUser(user)));
    }

    public List<ConvertedCurrency> loadHistoryByDate(User user, LocalDate localdate) {
        return convertedCurrencyRepository.findAll(where(hasUser(user).and(hasCreatedAt(localdate))));
    }

    public List<ConvertedCurrency> loadHistoryByDateAndFirstCurrency(User user, LocalDate localdate, String firstCurrency) {
        return convertedCurrencyRepository.findAll(where(hasFirstCurrency(firstCurrency).and(hasUser(user).and(hasCreatedAt(localdate)))));
    }

    public List<ConvertedCurrency> loadHistoryByDateAndSecondCurrency(User user, LocalDate localdate, String secondCurrency) {
        return convertedCurrencyRepository.findAll(where(hasUser(user).and(hasCreatedAt(localdate).and(hasSecondCurrency(secondCurrency)))));
    }

    public List<ConvertedCurrency> loadHistoryByAllParams(User user, LocalDate localdate, String firstCurrency, String secondCurrency) {
        return convertedCurrencyRepository.findAll(
                where(hasUser(user)
                        .and(hasSecondCurrency(secondCurrency)
                                .and(hasCreatedAt(localdate).and(hasFirstCurrency(firstCurrency))))));
    }

    public List<ConvertedCurrency> loadHistoryByFirstCurrencyAndSecondCurrency(User user, String firstCurrency, String secondCurrency) {
        return convertedCurrencyRepository.findAll(where(hasUser(user).and(hasFirstCurrency(firstCurrency)
        .and(hasSecondCurrency(secondCurrency)))));
    }

    public List<ConvertedCurrency> loadHistoryByFirstCurrency(User user, String firstCurrency) {
        return convertedCurrencyRepository.findAll(where(hasUser(user).and(hasFirstCurrency(firstCurrency))));
    }

    public List<ConvertedCurrency> loadHistoryBySecondCurrency(User user, String secondCurrency) {
        return convertedCurrencyRepository.findAll(where(hasUser(user).and(hasSecondCurrency(secondCurrency))));
    }
}
