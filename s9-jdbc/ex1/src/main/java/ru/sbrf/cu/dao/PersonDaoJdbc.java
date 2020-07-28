package ru.sbrf.cu.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sbrf.cu.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class PersonDaoJdbc implements PersonDao {

    private final NamedParameterJdbcOperations jdbc;

    public PersonDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public void insert(Person person) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", person.getId());
        params.put("name", person.getName());
        jdbc.update("insert into persons (`ID`,`NAME`) values (:id,:name)", params);
    }

    @Override
    public List<Person> getAll() {
        return jdbc.query("select * from persons", new PersonMapper());
    }

    @Override
    public Person getById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("select * from persons where id = :id",
                params,
                new PersonMapper());
    }

    @Override
    public void delete(Person person) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", person.getId());
        jdbc.update("delete from persons where id = :id", params);
    }

    @Override
    public long insertAuto(Person person) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", person.getName());
       // final Map<String, Object> params = new HashMap<>(2);
        KeyHolder kh = new GeneratedKeyHolder();
      //  params.put("id", );
        //params.put("name", person.getName());
        jdbc.update("insert into persons (`NAME`) values (:name)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public int count(){
       return  jdbc.queryForObject("select count(*) from persons", new HashMap<>(), Integer.class);
    }

    private static class PersonMapper implements RowMapper<Person>{
        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            var id = resultSet.getLong("id");
            var name = resultSet.getString("name");
            return new Person(id, name);
        }
    }
}
