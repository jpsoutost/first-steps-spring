package academy.mindswap.restfulwebservice.persistence.repositories;

import academy.mindswap.restfulwebservice.persistence.models.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
@Profile({"prod", "docker", "k8s"})
public interface MongoUserRepository extends BaseRepository, ReactiveMongoRepository<User, String> {
    Mono<User> findById(String id);

    @Override
    Mono<User> insert (User newUser);
}
