package academy.mindswap.restfulwebservice.persistence.repositories;

import academy.mindswap.restfulwebservice.persistence.models.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseRepository {
    Flux<User> findAll();

    Mono<User> insert(User newUser);

    Mono<User> findById(String id);
}
