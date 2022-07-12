package academy.mindswap.sportsdatabase.services;

import academy.mindswap.sportsdatabase.commands.UserDto;
import academy.mindswap.sportsdatabase.persistence.models.PersonalData;
import academy.mindswap.sportsdatabase.persistence.repositories.MongoDataRepository;
import brave.sampler.Sampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataService {

    @Value("${DOCKER_URL}")
    private String USER_NAME_URL;

    private final MongoDataRepository dataRepository;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    public DataService(@Lazy MongoDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    private String getUserNameUrl(String userId) {
        return USER_NAME_URL + userId;
    }

    private String findUserName(String userID){

        String url = getUserNameUrl(userID);

        UserDto user = restTemplate.getForObject(url, UserDto.class);

        return user.getName();
    }

    public Mono<PersonalData> getData(String id){
        LOG.info("Inside method2");
        String userName = findUserName(id);
        LOG.info("The response received by method2 is " + userName);
        return this.dataRepository.findByUserName(userName);
    }

    public Flux<PersonalData> findAll(){
        return this.dataRepository.findAll();
    }

}
