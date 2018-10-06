package org.emaginalabs.reactiveapi.repository;

import org.emaginalabs.reactiveapi.model.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * User: jose
 * Date: 06/10/2018
 * Time: 18:36
 */
public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
}
