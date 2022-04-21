package com.business.service.favorites.services;

import com.business.service.favorites.entities.Favorite;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteService {

    Flux<Favorite> findAll();
    Mono<Favorite> save(Favorite favorite);

}
