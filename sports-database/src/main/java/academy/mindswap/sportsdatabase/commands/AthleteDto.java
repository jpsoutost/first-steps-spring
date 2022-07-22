package academy.mindswap.sportsdatabase.commands;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AthleteDto {
    private String name;
    private String sport;
    private Integer age;

    @Override
    public String toString() {
        return "AthleteDto{" +
                "name='" + name + '\'' +
                ", team='" + sport + '\'' +
                ", age=" + age +
                '}';
    }
}
