package com.experience.integration.service.payment.repositories;

import com.experience.integration.service.payment.entities.Favorite;
import reactor.core.publisher.Mono;

public interface FavoriteRepository {

    Mono<Favorite> save(Favorite favorite, String authToken);

}
