package ru.repilov.library.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.repilov.library.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny().orElse(null);
    }

    public void save(@NotNull Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES(full_name, year_of_birth) VALUES(?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int id, @NotNull Person updPerson) {
        jdbcTemplate.update("UPDATE person SET full_name=?, year_of_birth=? WHERE id=?",
                updPerson.getFullName(), updPerson.getYearOfBirth());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}