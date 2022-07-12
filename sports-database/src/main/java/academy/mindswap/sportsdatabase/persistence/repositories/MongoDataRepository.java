package academy.mindswap.sportsdatabase.persistence.repositories;

import academy.mindswap.sportsdatabase.persistence.models.PersonalData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface MongoDataRepository extends ReactiveMongoRepository<PersonalData, String> {
    Mono<PersonalData> findByUserName(String userName);

}
