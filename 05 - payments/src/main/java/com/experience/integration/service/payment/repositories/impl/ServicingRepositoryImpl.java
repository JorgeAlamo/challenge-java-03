package com.experience.integration.service.payment.repositories.impl;

import com.experience.integration.service.payment.core.exceptions.GenericBaseException;
import com.experience.integration.service.payment.entities.Servicing;
import com.experience.integration.service.payment.repositories.ServicingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Repository
public class ServicingRepositoryImpl implements ServicingRepository {

    private final WebClient webClient;

    public ServicingRepositoryImpl(
            Builder builder,
            @Value("${service.url.api.servicePayment}") String urlApiServicePayment){
        log.info("urlApiServicing = " + urlApiServicePayment + "/services");

        this.webClient = builder
                .baseUrl(urlApiServicePayment + "/services")
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(5)))).build();

    }

    @Override
    public Flux<Servicing> findAll(String authToken) {
        return this.webClient.get()
                .accept(APPLICATION_JSON)
                .header("Authorization", authToken)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response-> Mono.error(new GenericBaseException("Server error")))
                .bodyToFlux(Servicing.class)
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(2))
                                .doBeforeRetry(signal ->  log.info("LOG BEFORE RETRY = " + signal))
                                .doAfterRetry(signal ->  log.info("LOG AFTER RETRY = " + signal))
                )
                .doOnError(inf -> log.info("LOG ERROR: Flux<Servicing> findAll()"))
                .doOnComplete(() -> log.info("LOG SUCCESS: Flux<Servicing> findAll()"));
    }

    @Override
    public Flux<Servicing> findByChannel(String code, String authToken) {
        return this.webClient.get()
                .uri("/channel/{code}", code)
                .accept(APPLICATION_JSON)
                .header("Authorization", authToken)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response-> Mono.error(new GenericBaseException("Server error")))
                .bodyToFlux(Servicing.class)
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(2))
                                .doBeforeRetry(signal ->  log.info("LOG BEFORE RETRY = " + signal))
                                .doAfterRetry(signal ->  log.info("LOG AFTER RETRY = " + signal))
                )
                .doOnError(inf -> log.info("LOG ERROR: Flux<Servicing> findByChannel"))
                .doOnComplete(() -> log.info("LOG SUCCESS: Flux<Servicing> findByChannel"));
    }

    @Override
    public Mono<Servicing> findByCode(String code, String authToken) {
        return this.webClient.get()
                .uri("/{code}", code)
                .accept(APPLICATION_JSON)
                .header("Authorization", authToken)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response-> Mono.error(new GenericBaseException("Server error")))
                .bodyToMono(Servicing.class)
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(2))
                                .doBeforeRetry(signal ->  log.info("LOG BEFORE RETRY = " + signal))
                                .doAfterRetry(signal ->  log.info("LOG AFTER RETRY = " + signal))
                )
                .doOnError(inf -> log.info("LOG ERROR: Mono<Servicing> findByCode"))
                .doOnSuccess(inf -> log.info("LOG SUCCESS: Mono<Servicing> findByCode"));
    }

}
