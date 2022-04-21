package com.business.service.payment.repositories;

import com.business.service.payment.entities.Servicing;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ServicingRepository extends ReactiveMongoRepository<Servicing, String> {

    @Query(value = "{'channel.code': ?0}")
    Flux<Servicing> findByChannel(String channelCod);
    @Query(value = "{'code': ?0}")
    Mono<Servicing> findByCode(String code);

}
