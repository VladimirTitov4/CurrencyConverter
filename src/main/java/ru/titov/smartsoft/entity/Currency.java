package ru.titov.smartsoft.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "quotes")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String valuteId;
    private String numCode;
    private String charCode;
    private int nominal;
    private String name;
    private String value;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CURRENCY_FID", referencedColumnName = "CURRENCY_ID")
    private Quote quote;
}
