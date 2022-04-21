package com.experience.integration.service.payment.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Favorite {

    private Integer id;
    private String serviceCod;
    private String serviceType;
    private String name;
    private String clientCod;

}
