package aop.service;

import aop.aspect.LoggingEnabled;
import aop.dao.PersonDao;
import aop.domain.Person;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@Service
//@EnableAspectJAutoProxy
public class PersonServiceImpl implements PersonService {

    private final PersonDao dao;

       public PersonServiceImpl(PersonDao dao) {
        this.dao = dao;
    }

    @LoggingEnabled
    public Person getByName(String name) {
        return dao.findByName(name);
    }
}
