package ru.titov.smartsoft.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class ConvertCurrency {

    public String firstValue;
    public String SecondValue;
    public Long result;
}
