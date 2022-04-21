package com.business.service.payment.services;

import com.business.service.payment.entities.Servicing;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicingService {

    Flux<Servicing> findAll();
    Flux<Servicing> findByChannel(String channelCod);
    Mono<Servicing> findByCode(String code);
    Mono<Servicing> save(Servicing service);

}
