package com.experience.integration.service.payment.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {

    private String id;
    private String supplyNumber;
    private String serviceCod;
    private BigDecimal amount;
    private String clientCod;
    private Boolean isFavorite;
    @JsonInclude(Include.NON_NULL)
    private String favoriteName;

}
