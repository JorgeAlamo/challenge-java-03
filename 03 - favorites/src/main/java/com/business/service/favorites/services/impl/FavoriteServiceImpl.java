package com.business.service.favorites.services.impl;

import com.business.service.favorites.entities.Favorite;
import com.business.service.favorites.repositories.FavoriteRepository;
import com.business.service.favorites.services.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    public Flux<Favorite> findAll() {
        return this.favoriteRepository.findAll();
    }

    @Override
    public Mono<Favorite> save(Favorite favorite) {
        return this.favoriteRepository.save(favorite);
    }

}
