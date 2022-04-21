package com.business.service.payment.handlers;

import com.business.service.payment.entities.Servicing;
import com.business.service.payment.services.ServicingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class ServicingHandler {

    @Autowired
    private ServicingService servicingService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return this.servicingService.findAll()
                .collectList()
                .flatMap(list-> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(list), Servicing.class));
    }

    public Mono<ServerResponse> findByChannel(ServerRequest request) {
        return this.servicingService.findByChannel(
                request.pathVariable("channelCod").toUpperCase())
                .collectList()
                .flatMap(list-> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(list), Servicing.class));
    }

    public Mono<ServerResponse> findByCode(ServerRequest request) {
        return this.servicingService.findByCode(
                request.pathVariable("code"))
                .flatMap(service -> ServerResponse.ok()
                        .body(Mono.just(service), Servicing.class))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Servicing.class)
                .flatMap(service -> this.servicingService.save(service))
                .flatMap(service -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(service), Servicing.class));
    }

}
