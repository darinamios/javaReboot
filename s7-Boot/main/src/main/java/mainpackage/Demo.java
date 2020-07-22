package mainpackage;

import mainpackage.configs.YamlProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class Demo {

    public static void main(String[] args) {
        SpringApplication.run(Demo.class, args);
    }
}
