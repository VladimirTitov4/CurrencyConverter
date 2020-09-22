package ru.titov.smartsoft.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class ConvertedCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstCurrency;
    private String secondCurrency;
    private String amountToConvert;
    private String result;

    @CreationTimestamp
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_FID", referencedColumnName = "USER_ID")
    private User user;
}
