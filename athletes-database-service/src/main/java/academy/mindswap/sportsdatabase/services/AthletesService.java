package academy.mindswap.sportsdatabase.services;

import academy.mindswap.sportsdatabase.persistence.models.Athlete;
import academy.mindswap.sportsdatabase.persistence.repositories.AthletesRepository;
import brave.sampler.Sampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AthletesService {

    private AthletesRepository repository;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public AthletesService(@Lazy AthletesRepository repository) {
        this.repository = repository;
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    public Mono<Athlete> getAthletesData(String[] names){
        LOG.info("Inside method3");
        Mono<Athlete> athletes = repository.findByNames(names);
        LOG.info("The response was received by method3");
        return athletes;
    }

}
