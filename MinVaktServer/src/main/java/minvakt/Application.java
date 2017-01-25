package minvakt;

import minvakt.controller.ChangeRequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;

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
            Shift shift = new Shift();
            shift.setShiftId(6);
            shift.setFromTime(LocalDateTime.of(2017, 1, 18, 22, 0));

            log.info("testing: {}",test.getEmployeesAvailableForShift(shift));
        };
    }*/
}