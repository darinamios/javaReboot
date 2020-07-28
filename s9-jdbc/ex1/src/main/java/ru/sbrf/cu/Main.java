package ru.sbrf.cu;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.sbrf.cu.dao.PersonDao;
import ru.sbrf.cu.domain.Person;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);

        PersonDao dao = context.getBean(PersonDao.class);
        System.out.println("persons in db: " + dao.count());
        var alex = new Person(2L, "Alex");
        dao.insert(alex);
        System.out.println("persons in db: " + dao.count());
        System.out.println(dao.getAll());
        dao.delete(alex);
        System.out.println(dao.getAll());
        var lena = new Person(0, "Lena");
        long id = dao.insertAuto(lena);
        System.out.println(dao.getById(id));
        Console.main(args);
    }
}
