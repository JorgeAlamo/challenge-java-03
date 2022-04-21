package com.experience.integration.service.payment.services;

import com.experience.integration.service.payment.entities.Servicing;
import com.experience.integration.service.payment.entities.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {

    Flux<Servicing> findServiceByChannel(String channelCod, String authToken);
    Mono<Transaction> execute(Transaction transaction, String authToken);

}
