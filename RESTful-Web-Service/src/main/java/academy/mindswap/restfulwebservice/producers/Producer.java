package academy.mindswap.restfulwebservice.producers;

import academy.mindswap.restfulwebservice.persistence.models.User;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class Producer {
    private static final String TOPIC = "test_topic";

    @Autowired
    private KafkaTemplate<String,User> kafkaTemplate;

    public User sendMessage(User user){
        this.kafkaTemplate.send(TOPIC, user);

        return user;
    }

    @Bean
    public NewTopic createTopic(){
        return new NewTopic(TOPIC,3,(short) 1);
    }

}

