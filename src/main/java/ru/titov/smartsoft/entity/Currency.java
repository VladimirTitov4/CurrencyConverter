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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String valuteId;
    private int numCode;
    private String charCode;
    private int nominal;
    private String name;
    private Double value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CURRENCY_FID", referencedColumnName = "CURRENCY_ID")
    private Quote quote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_FID", referencedColumnName = "USER_ID")
    private User user;
}
