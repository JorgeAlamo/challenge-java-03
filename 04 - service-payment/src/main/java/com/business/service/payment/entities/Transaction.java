package com.business.service.payment.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "transactions")
public class Transaction {

    @Id
    private String id;
    private String supplyNumber;
    private String serviceCod;
    private BigDecimal amount;
    private String clientCod;
    private Boolean isFavorite;
    @JsonInclude(Include.NON_NULL)
    private String favoriteName;

}
