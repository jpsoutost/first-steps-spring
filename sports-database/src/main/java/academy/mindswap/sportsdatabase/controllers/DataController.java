package academy.mindswap.sportsdatabase.controllers;

import academy.mindswap.sportsdatabase.commands.AthleteDto;
import academy.mindswap.sportsdatabase.persistence.models.PersonalData;
import academy.mindswap.sportsdatabase.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @QueryMapping
    Flux<PersonalData> personaData(){
        return this.dataService.findAll();
    }

    @QueryMapping
    Mono<PersonalData> personalDataByUserName(@Argument String id) {
        return this.dataService.getData(id);
    }

    @SchemaMapping(typeName = "personalData")
    Flux<AthleteDto> favouriteAthletes(PersonalData personalData){
        return dataService.athleteData(personalData);
    }

}


