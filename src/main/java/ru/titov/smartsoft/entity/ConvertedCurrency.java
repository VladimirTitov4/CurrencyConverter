package ru.titov.smartsoft.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Entity
public class ConvertedCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal firstCurrencyValue;
    private BigDecimal secondCurrencyValue;
    private BigDecimal firstCurrencyNominal;
    private BigDecimal secondCurrencyNominal;
    private BigDecimal amount;
    private BigDecimal result;

    public ConvertedCurrency(BigDecimal firstCurrencyValue,
                             BigDecimal secondCurrencyValue,
                             BigDecimal firstCurrencyNominal,
                             BigDecimal secondCurrencyNominal,
                             BigDecimal value
    ) {
        this.firstCurrencyValue = firstCurrencyValue;
        this.secondCurrencyValue = secondCurrencyValue;
        this.firstCurrencyNominal = firstCurrencyNominal;
        this.secondCurrencyNominal = secondCurrencyNominal;
        this.amount = value;
        result = firstCurrencyValue.multiply(secondCurrencyNominal).multiply(amount)
                .divide(secondCurrencyValue.multiply(firstCurrencyNominal), 4, RoundingMode.HALF_EVEN);
    }
}
