package com.business.service.favorites.repositories;

import com.business.service.favorites.entities.Favorite;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FavoriteRepository extends ReactiveCrudRepository<Favorite, Integer> {
}
