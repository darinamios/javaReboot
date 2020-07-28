package ru.sbrf.cu.dao;

import ru.sbrf.cu.domain.Person;

import java.util.List;

public interface PersonDao {

    int count();

    void insert(Person person);
    List<Person> getAll();
    Person getById(long id);
    void delete(Person person);
    long insertAuto(Person person);
}
