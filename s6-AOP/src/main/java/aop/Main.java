package aop;

import aop.domain.Person;
import aop.service.PersonService;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@EnableAspectJAutoProxy
@Configuration
//@ComponentScan
public class Main {

    public static void main(String[] args) {
       /* AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        PersonService service = context.getBean(PersonService.class);

        Person ivan = service.getByName("Ivan");
        System.out.println("name: " + ivan.getName() + " age: " + ivan.getAge());*/
        var context = new ClassPathXmlApplicationContext("/context.xml");
        var service = context.getBean(PersonService.class);
        var ivan = service.getByName("Ivan");
        System.out.println("name: " + ivan.getName() + " age: " + ivan.getAge());
    }
}
