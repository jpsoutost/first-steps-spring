package academy.mindswap.sportsdatabase.controllers;

import academy.mindswap.sportsdatabase.persistence.models.Athlete;
import academy.mindswap.sportsdatabase.services.AthletesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.print.DocFlavor;

@Controller
public class AthletesController {

    @Autowired
    private AthletesService athletesService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @QueryMapping
    Mono<Athlete> athleteData(@Argument String names) {
        LOG.info("Inside method3");
        String[] arr = new String[1];
        arr[0]=names;
        return this.athletesService.getAthletesData(arr);
    }

}


