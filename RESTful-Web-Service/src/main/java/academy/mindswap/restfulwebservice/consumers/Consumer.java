package academy.mindswap.restfulwebservice.consumers;

import academy.mindswap.restfulwebservice.persistence.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class Consumer {

    @Autowired
    private CacheManager cacheManager;

    @KafkaListener(topics = "test_topic",groupId = "example")
    public void consumeMessage(User message){
        CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache("user_cache");
        caffeineCache.put(message.getId(), Mono.just(message));
    }

}