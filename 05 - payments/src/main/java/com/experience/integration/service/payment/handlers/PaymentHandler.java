package com.experience.integration.service.payment.handlers;

import com.experience.integration.service.payment.entities.Servicing;
import com.experience.integration.service.payment.entities.Transaction;
import com.experience.integration.service.payment.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class PaymentHandler {

    @Autowired
    private PaymentService paymentService;

    public Mono<ServerResponse> findByChannel(ServerRequest request) {
        return this.paymentService.findServiceByChannel(
                        request.pathVariable("code").toUpperCase(),
                        request.headers().header("Authorization").get(0))
                .collectList()
                .flatMap(list-> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(list), Servicing.class));
    }

    public Mono<ServerResponse> execute(ServerRequest request) {
        return request.bodyToMono(Transaction.class)
                .flatMap(transaction -> this.paymentService.execute(
                        transaction,
                        request.headers().header("Authorization").get(0)))
                .flatMap(transaction -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(transaction), Transaction.class));
    }

}
