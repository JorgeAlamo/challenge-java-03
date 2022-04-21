package com.experience.integration.service.payment.services.impl;

import com.experience.integration.service.payment.core.exceptions.GenericBaseException;
import com.experience.integration.service.payment.entities.Favorite;
import com.experience.integration.service.payment.entities.Servicing;
import com.experience.integration.service.payment.entities.Transaction;
import com.experience.integration.service.payment.repositories.FavoriteRepository;
import com.experience.integration.service.payment.repositories.ServicingRedisRepository;
import com.experience.integration.service.payment.repositories.TransactionRepository;
import com.experience.integration.service.payment.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private ServicingRedisRepository servicingRedisRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    public Flux<Servicing> findServiceByChannel(String channelCod, String authToken) {
        return this.servicingRedisRepository.findByChannel(channelCod, authToken);
    }

    @Override
    public Mono<Transaction> execute(Transaction transaction, String authToken) {
        return transaction.getIsFavorite() ?
                Mono.zip(
                        this.servicingRedisRepository
                                .findByCode(transaction.getServiceCod(), authToken)
                                .onErrorResume(error -> Mono.empty())
                                .switchIfEmpty(Mono.error(new GenericBaseException(NOT_FOUND, "Service not found")))
                                .flatMap(service ->
                                        this.favoriteRepository.save(Favorite.builder()
                                                .serviceCod(service.getCode())
                                                .serviceType(service.getType())
                                                .name(transaction.getFavoriteName())
                                                .clientCod(transaction.getClientCod()).build(), authToken)),
                        this.transactionRepository.save(transaction, authToken))
                        .flatMap(tuple -> {
                            Transaction resp = tuple.getT2();
                            return Mono.just(resp);
                        }) :
                this.servicingRedisRepository
                        .findByCode(transaction.getServiceCod(), authToken)
                        .onErrorResume(error -> Mono.empty())
                        .switchIfEmpty(Mono.error(new GenericBaseException(NOT_FOUND, "Service not found")))
                        .flatMap(service -> this.transactionRepository.save(transaction, authToken));
    }

}
