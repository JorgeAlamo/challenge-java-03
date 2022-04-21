package com.experience.integration.service.payment.repositories;

import com.experience.integration.service.payment.entities.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionRepository {

    Mono<Transaction> save(Transaction transaction, String authToken);

}
