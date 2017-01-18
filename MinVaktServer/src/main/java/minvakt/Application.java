package minvakt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class, RepositoryRestMvcAutoConfiguration.class})
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*@Bean
    public CommandLineRunner demo(EmployeeRepository userRepo, ShiftRepository shiftRepo, ShiftAssignmentRepository usRepo, CategoryRepository categoryRepo, ShiftController shiftController) {
        return (args) -> {
            // save a couple of customers
//            userRepo.save(new Employee("Jack", "Bauer", "test1@test.com", 98127331, "qwerty", 12));
//            userRepo.save(new Employee("Chloe", "O'Brian", "test2@test.com", 98127331, "qwerty", 12));
//            userRepo.save(new Employee("Kim", "Bauer", "test3@test.com", 98127331, "qwerty", 12));
//            userRepo.save(new Employee("David", "Palmer", "test4@test.com", 98127331, "qwerty", 12));
            *//*Employee user = new Employee("Michelle", "Dessler", "test5@test.com", 98127331, "qwerty", 12);
            userRepo.save(user);
*//*
            *//*LocalDate now = LocalDate.now();
            shiftRepo.save(new Shift(now, PredeterminedIntervals.DAYTIME));
            shiftRepo.save(new Shift(now, PredeterminedIntervals.MORNING));
            Shift shift = new Shift(now, PredeterminedIntervals.NIGHT);
            shiftRepo.save(shift);
            *//*
            //shift.getUsers().add(user);
*//*

            log.info("get user");
            User user1 = userRepo.findOne(2);
            log.info("get shift");
            Shift one = shiftRepo.findOne(6);
            log.info("create object");

            log.info("save object");
            shiftController.addUserToShift(one.getShiftId(),user1);
//            usRepo.save(shiftAssignment);
            log.info("save done");

*//*
            Shift one = shiftRepo.findOne(6);
//            log.info("Shift {},Users {}", one, one.getShiftAssignments().size());

            // fetch all customers
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (Employee aEmployee : userRepo.findAll()) {
                log.info(aEmployee.toString());
            }
            log.info("");
        };
    }*/
}