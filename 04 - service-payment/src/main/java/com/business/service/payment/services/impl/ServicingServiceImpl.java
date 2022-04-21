package com.business.service.payment.services.impl;

import com.business.service.payment.entities.Servicing;
import com.business.service.payment.repositories.ServicingRepository;
import com.business.service.payment.services.ServicingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ServicingServiceImpl implements ServicingService {

    @Autowired
    private ServicingRepository servicingRepository;

    @Override
    public Flux<Servicing> findAll() {
        return this.servicingRepository.findAll();
    }

    @Override
    public Flux<Servicing> findByChannel(String channelCod) {
        return this.servicingRepository.findByChannel(channelCod);
    }

    @Override
    public Mono<Servicing> findByCode(String code) {
        return this.servicingRepository.findByCode(code);
    }

    @Override
    public Mono<Servicing> save(Servicing service) {
        return this.servicingRepository.save(service);
    }

}
