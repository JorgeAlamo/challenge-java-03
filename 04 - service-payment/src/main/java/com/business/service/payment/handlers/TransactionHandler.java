package com.business.service.payment.handlers;

import com.business.service.payment.entities.Transaction;
import com.business.service.payment.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class TransactionHandler {

    @Autowired
    private TransactionService transactionService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return this.transactionService.findAll()
                .collectList()
                .flatMap(list-> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(list), Transaction.class));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Transaction.class)
                .flatMap(transaction -> this.transactionService.save(transaction))
                .flatMap(transaction -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(transaction), Transaction.class));
    }

}
