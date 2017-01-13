package minvakt;

import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.datamodel.UserShiftInfo;
import minvakt.datamodel.enums.EmployeeType;
import minvakt.datamodel.enums.PredeterminedIntervals;
import minvakt.datamodel.enums.ShiftType;
import minvakt.repos.ShiftRepository;
import minvakt.repos.UserRepository;
import minvakt.repos.UserShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepo, ShiftRepository shiftRepo, UserShiftRepository usRepo) {
        return (args) -> {
            // save a couple of customers
            userRepo.save(new User("Jack", "Bauer", "test1@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12));
            userRepo.save(new User("Chloe", "O'Brian", "test2@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12));
            userRepo.save(new User("Kim", "Bauer", "test3@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12));
            userRepo.save(new User("David", "Palmer", "test4@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12));
            User user = new User("Michelle", "Dessler", "test5@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12);
            userRepo.save(user);

            LocalDate now = LocalDate.now();
            shiftRepo.save(new Shift(now, PredeterminedIntervals.DAYTIME, ShiftType.AVAILABLE));
            shiftRepo.save(new Shift(now, PredeterminedIntervals.MORNING, ShiftType.AVAILABLE));
            Shift shift = new Shift(now, PredeterminedIntervals.NIGHT, ShiftType.AVAILABLE);
            shiftRepo.save(shift);
            //shift.getUsers().add(user);

            UserShiftInfo info = new UserShiftInfo(user, shift);
            usRepo.save(info);


            Shift one = shiftRepo.findOne(shift.getShiftId());

            /*one.getUsers().add(userRepo.findOne(1));
            */
            log.info("Shift {},Users {}", one, one.getUsers().toArray()[0]);

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (User aUser : userRepo.findAll()) {
                log.info(aUser.toString());
            }
            log.info("");

            /*// fetch an individual customer by ID
            Customer customer = repository.findOne(1L);
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            for (Customer bauer : repository.findByLastName("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");*/
        };
    }
}