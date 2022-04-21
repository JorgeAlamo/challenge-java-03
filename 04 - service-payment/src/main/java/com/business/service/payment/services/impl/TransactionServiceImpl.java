package com.business.service.payment.services.impl;

import com.business.service.payment.entities.Transaction;
import com.business.service.payment.repositories.TransactionRepository;
import com.business.service.payment.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Flux<Transaction> findAll() {
        return this.transactionRepository.findAll();
    }

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

}
