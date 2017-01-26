package minvakt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class, RepositoryRestMvcAutoConfiguration.class})
public class Application {

    public static final int HOURS_IN_WEEK = 38;
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*@Bean
    public CommandLineRunner demo(JooqRepository test) {
        return (args) -> {
            Map<Integer, Duration> hoursWorked = test.getHoursWorked(LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.MONTHS));

            log.info("testing: {}", hoursWorked);
        };
    }*/
}