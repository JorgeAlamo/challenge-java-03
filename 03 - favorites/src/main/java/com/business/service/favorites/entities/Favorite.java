package com.business.service.favorites.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table("favorite")
public class Favorite {

    @Id
    @Column("id")
    private Integer id;
    @Column("service_cod")
    private String serviceCod;
    @Column("type")
    private String serviceType;
    @Column("name")
    private String name;
    @Column("client_cod")
    private String clientCod;

}
