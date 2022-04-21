package com.experience.integration.service.payment.repositories;

import com.experience.integration.service.payment.entities.Servicing;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicingRedisRepository {

    Flux<Servicing> findByChannel(String channelCod, String authToken);
    Mono<Servicing> findByCode(String code, String authToken);

}
