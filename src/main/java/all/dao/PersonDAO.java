package all.dao;

import all.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addNewUser(Person person) {
        jdbcTemplate.update("INSERT INTO person(person_name, year_birth) VALUES(?, ?)",
                person.getPerson_name(), person.getYear_birth());
    }
    public List<Person> getAllPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPersonById(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }

    public void deleteUser(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id = ?", id);
    }

    public void editUser(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET person_name = ?, year_birth = ? WHERE person_id = ?",
                person.getPerson_name(), person.getYear_birth(), id);
    }
}
