package com.experience.integration.service.payment.repositories.impl;

import com.experience.integration.service.payment.core.exceptions.GenericBaseException;
import com.experience.integration.service.payment.entities.Favorite;
import com.experience.integration.service.payment.repositories.FavoriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Repository
public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final WebClient webClient;

    public FavoriteRepositoryImpl(Builder builder,
                                  @Value("${service.url.api.favorite}")
                                          String urlApiFavorite){
        log.info("urlApiFavorite = " + urlApiFavorite);

        this.webClient = builder.baseUrl(urlApiFavorite)
                .clientConnector(
                        new ReactorClientHttpConnector(HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(5)))).build();
    }

    @Override
    public Mono<Favorite> save(Favorite favorite, String authToken) {
        return this.webClient.post()
                .accept(APPLICATION_JSON)
                .header("Authorization", authToken)
                .body(Mono.just(favorite), Favorite.class)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response-> Mono.error(new GenericBaseException("Server error")))
                .bodyToMono(Favorite.class)
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(2))
                                .doBeforeRetry(signal -> log.info("LOG BEFORE RETRY = " + signal))
                                .doAfterRetry(signal -> log.info("LOG AFTER RETRY = " + signal))
                )
                .doOnError(inf -> log.info("LOG ERROR: Mono<Favorite> save"))
                .doOnSuccess(inf -> log.info("LOG SUCCESS: Mono<Favorite> save"));
    }

}
