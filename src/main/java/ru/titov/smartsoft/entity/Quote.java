package ru.titov.smartsoft.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "BaseQuote")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "general-generator")
    @SequenceGenerator(name = "general-generator", sequenceName = "SQ_GENERAL", allocationSize = 1)
    @Column(name = "CURRENCY_ID", nullable = false, unique = true)
    private Long id;
    private String date;
    private String name;

    @OneToMany(mappedBy = "quote", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Currency> currencies;
}
