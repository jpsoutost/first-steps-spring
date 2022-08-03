package academy.mindswap.restfulwebservice.persistence.repositories;

import academy.mindswap.restfulwebservice.persistence.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Getter
@Setter
@Profile("dev")
public class UserRepository implements BaseRepository {

    public Map<String, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
        loadData();
    }

    public void loadData(){
        users.put("1", new User("1", "João"));
        users.put("2", new User("2", "André"));
        users.put("3", new User("3", "Martim"));
        users.put("4", new User("4", "Tiago"));
        users.put("5", new User("5", "Paulo"));
    }
    public Flux<User> findAll(){
        return Flux.fromIterable(users.values());
    }

    public Mono<User> insert(User newUser){

        newUser.setId(String.valueOf(users.size()+1));
        users.put(newUser.getId(), newUser);

        return Mono.just(newUser);
    }

    public  Mono<User> findById(String id){
        return Mono.just(users.get(id));
    }

}
