package aop.dao;

import aop.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDaoSimple implements PersonDao {
    private Integer defaultAge;

    public Person findByName(String name) {
        return new Person(name, defaultAge);
    }

    public void setDefaultAge(Integer age){
        defaultAge = age;
    }
}
