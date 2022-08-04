package academy.mindswap.sportsdatabase.services;

import academy.mindswap.sportsdatabase.commands.AthleteDto;
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
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataService {

    @Value("${DOCKER_URL}")
    private String USER_NAME_URL;

    private final MongoDataRepository dataRepository;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HttpGraphQlClient http;

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
        return dataRepository.findByUserName(userName);
    }

    public Flux<PersonalData> findAll(){
        return this.dataRepository.findAll();
    }

    public Flux<AthleteDto> athleteData(PersonalData data){
        LOG.info("Inside method3");
        String[] athletes = data.getFavouriteAthletes();
        List<AthleteDto> athleteDtos = new ArrayList<>();

        for (String athlete:athletes){

            String document = String.format("{athleteData(names: \"%s\"){ name,sport,age}}",athlete);
            System.out.println(document);

            AthleteDto athleteDto = http.document(document).retrieve("athleteData").toEntity(AthleteDto.class).block();

            athleteDtos.add(athleteDto);

        }
        LOG.info("The response received by method3 was" + athleteDtos);
        return Flux.fromIterable(athleteDtos);
    }

}
