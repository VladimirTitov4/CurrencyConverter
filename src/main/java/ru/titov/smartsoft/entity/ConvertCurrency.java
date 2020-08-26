package ru.titov.smartsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConvertCurrency {

    public Currency firstCurrency;
    public Currency SecondCurrency;

}
