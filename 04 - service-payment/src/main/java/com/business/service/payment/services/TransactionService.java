package com.business.service.payment.services;

import com.business.service.payment.entities.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Flux<Transaction> findAll();
    Mono<Transaction> save(Transaction transaction);

}
