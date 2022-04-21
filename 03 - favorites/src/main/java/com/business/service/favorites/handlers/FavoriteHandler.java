package com.business.service.favorites.handlers;

import com.business.service.favorites.entities.Favorite;
import com.business.service.favorites.services.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class FavoriteHandler {

    @Autowired
    private FavoriteService favoriteService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return this.favoriteService.findAll()
                .collectList()
                .flatMap(list-> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok()
                            .contentType(APPLICATION_JSON)
                            .body(Mono.just(list), Favorite.class));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Favorite.class)
                .flatMap(favorite -> this.favoriteService.save(favorite))
                .flatMap(favorite -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(favorite), Favorite.class));
    }

}
