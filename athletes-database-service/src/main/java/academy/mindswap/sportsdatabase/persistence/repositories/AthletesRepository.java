package academy.mindswap.sportsdatabase.persistence.repositories;

import academy.mindswap.sportsdatabase.persistence.models.Athlete;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class AthletesRepository{

    public Map<String, Athlete> athletes;

    public AthletesRepository() {
        this.athletes = new ConcurrentHashMap<>();
        loadData();
    }

    public void loadData(){
        athletes.put("Mike Tyson", new Athlete("Mike Tyson", "Boxing", 60));
        athletes.put("Roger Federer", new Athlete("Roger Federer", "Tennis", 38));
        athletes.put("Victor Iturriza", new Athlete("Victor Iturriza", "Handball", 30));
        athletes.put("Tadej Pogacar", new Athlete("Tadej Pogacar", "Cycling", 25));
        athletes.put("Cristiano Ronaldo", new Athlete("Cristiano Ronaldo", "Football", 37));
    }

    public  Mono<Athlete> findByNames(String[] names){
        Athlete at = athletes.get(names[0]);
        return Mono.just(at);
    }

}
