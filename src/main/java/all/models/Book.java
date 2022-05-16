package all.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private int book_id;

    @NotEmpty(message = "Author name should be not empty")
    @Size(min = 2, max = 90, message = "Author name length should be at least 2 and no longer than 90 character")
    private String author;

    @NotEmpty(message = "Book name should be not empty")
    @Size(min = 2, max = 40, message = "Book name length should be at least 2 and no longer than 40 character")
    private String book_name;

    @Min(value = 1800, message = "Release year should be later than 1800")
    private int release_date;

    private Optional<Integer> person_id;
}
