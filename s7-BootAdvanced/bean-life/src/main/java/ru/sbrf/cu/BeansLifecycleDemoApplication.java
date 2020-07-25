package ru.sbrf.cu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.sbrf.cu.domain.Phone;

@SpringBootApplication
public class BeansLifecycleDemoApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BeansLifecycleDemoApplication.class, args);
		try {
			Phone phone = ctx.getBean(Phone.class);
			phone.callFavoriteNumber();
		}catch (Exception e) {
		}
	}

}
