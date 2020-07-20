package aop.dao;


import aop.domain.Person;

public interface PersonDao {

    Person findByName(String name);
}
