package com.experience.integration.service.payment.repositories.impl;

import com.experience.integration.service.payment.entities.Servicing;
import com.experience.integration.service.payment.repositories.ServicingRedisRepository;
import com.experience.integration.service.payment.repositories.ServicingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Repository
public class ServicingRedisRepositoryImpl implements ServicingRedisRepository {

    private static final String SERVICING_PREFIX_KEY = "servicing";

    @Autowired
    private ReactiveRedisOperations<String, Servicing> reactiveRedisOperations;

    @Autowired
    private ServicingRepository servicingRepository;

    @Override
    public Flux<Servicing> findByChannel(String code, String authToken) {
        return this.reactiveRedisOperations.<String, Servicing>opsForHash()
                .values(SERVICING_PREFIX_KEY)
                .collectList()
                .flatMap(list -> {
                    if (!list.isEmpty())
                        log.info("LOG SUCCESS: (redis) Flux<Servicing> findByChannel");
                    return Mono.just(list);
                }).flatMapMany(Flux::fromIterable)
                .filter(service ->
                        service.getChannel().getCode().equals(code))
                .doOnError(inf -> log.info("LOG ERROR: (redis) Flux<Servicing> findByChannel"))
                .switchIfEmpty(
                    this.servicingRepository
                            .findAll(authToken)
                            .flatMap(service ->
                                this.reactiveRedisOperations.<String, Servicing>opsForHash()
                                        .put(SERVICING_PREFIX_KEY, service.getId(), service))
                            .flatMap(flag -> this.reactiveRedisOperations.expire(SERVICING_PREFIX_KEY, Duration.ofMinutes(5)))
                            .doOnError(inf -> log.info("LOG ERROR: (redis update) Flux<Servicing> findByChannel"))
                            .doOnComplete(() -> log.info("LOG SUCCESS: (redis update) Flux<Servicing> findByChannel"))
                            .switchMap(flag -> this.servicingRepository.findByChannel(code, authToken)));
    }

    @Override
    public Mono<Servicing> findByCode(String code, String authToken) {
        return this.reactiveRedisOperations.<String, Servicing>opsForHash()
                .values(SERVICING_PREFIX_KEY)
                .filter(service -> service.getCode().equals(code))
                .collectList()
                .flatMap(list -> {
                    if (!list.isEmpty()) {
                        log.info("LOG SUCCESS: (redis) Mono<Servicing> findByCode");
                        return Mono.just(list.get(0));
                    } else {
                        return Mono.empty();
                    }
                })
                .doOnError(inf -> log.info("LOG ERROR: (redis) Mono<Servicing> findByCode"))
                .switchIfEmpty(
                        this.servicingRepository
                                .findAll(authToken)
                                .flatMap(service ->
                                        this.reactiveRedisOperations.<String, Servicing>opsForHash()
                                                .put(SERVICING_PREFIX_KEY, service.getId(), service))
                                .flatMap(flag -> this.reactiveRedisOperations.expire(SERVICING_PREFIX_KEY, Duration.ofMinutes(5)))
                                .doOnError(inf -> log.info("LOG ERROR: (redis update) Flux<Servicing> findByChannel"))
                                .doOnComplete(() -> log.info("LOG SUCCESS: (redis update) Flux<Servicing> findByChannel"))
                                .switchMap(flag -> this.servicingRepository.findByCode(code, authToken))
                                .collectList()
                                .flatMap(list -> {
                                    if (!list.isEmpty()) {
                                        return Mono.just(list.get(0));
                                    } else {
                                        return Mono.empty();
                                    }
                                }));
    }
}
