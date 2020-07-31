package ru.pogo.sbrf.cu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.models.ATMImpl;

@SpringBootApplication
public class ATMDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ATMDemo.class, args);
        ATM atm = ctx.getBean(ATMImpl.class);
        System.out.println(atm.getTotalSum());
    }
}
