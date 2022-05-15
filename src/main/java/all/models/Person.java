package all.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private int person_id;

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 2, max = 90, message = "Name length should be at least 2 and no longer than 90 character")
    private String person_name;

    @Min(value = 1880, message = "Year of birth should be later than 1880")
    private int year_birth;
}
