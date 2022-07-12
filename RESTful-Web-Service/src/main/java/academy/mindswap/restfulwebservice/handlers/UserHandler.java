package academy.mindswap.restfulwebservice.handlers;

import academy.mindswap.restfulwebservice.persistence.models.User;
import academy.mindswap.restfulwebservice.persistence.repositories.BaseRepository;
import academy.mindswap.restfulwebservice.producers.Producer;
import brave.sampler.Sampler;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@JsonSerialize
public class UserHandler {

    @Autowired
    private CacheManager cacheManager;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final BaseRepository repository;

    private final Producer producer;

    @Autowired
    public UserHandler(@Lazy BaseRepository repository, Producer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    public Flux<User> findAll() {
        LOGGER.info("getAllUsers");
        return repository.findAll();
    }

    public Mono<User> createUser(User newUser) {

        if (newUser.getName() == "" || newUser.getName() == null || newUser.getId()!=null) {
            LOGGER.warn("Error on createUser: Invalid Name or Id");
            return null;
        }

        LOGGER.info("createUser:" + newUser.getName());
        newUser.setId(null);
        return repository.insert(newUser)
                        .mapNotNull(producer::sendMessage);
    }

    @Cacheable("user_cache")
    public Mono<User> getUserById(String id){

        LOG.info("going to user repository");
        Mono<User> userMono = repository.findById(id);

        if (userMono == null) {
            LOGGER.warn("getUserById (Invalid ID): " + id);
            return null;
        }

        LOG.info("getUserById:" + id);
        return userMono;

    }

}