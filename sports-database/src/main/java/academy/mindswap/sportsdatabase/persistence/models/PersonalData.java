package academy.mindswap.sportsdatabase.persistence.models;

import academy.mindswap.sportsdatabase.commands.AthleteDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Document("PersonalData")
public class PersonalData {

    @Id
    private String id;
    private String userName;
    private String[] favouriteAthletes;
    private String[] favouriteTeams;
    private String[] favouriteSports;
}
