package minvakt;

import minvakt.datamodel.User;
import minvakt.datamodel.enums.EmployeeType;
import minvakt.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new User("Jack", "Bauer", "test1@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12));
            repository.save(new User("Chloe", "O'Brian", "test2@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12));
            repository.save(new User("Kim", "Bauer", "test3@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12));
            repository.save(new User("David", "Palmer", "test4@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12));
            repository.save(new User("Michelle", "Dessler", "test5@test.com", 98127331, "qwerty", EmployeeType.ASSISTENT, 12));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (User user : repository.findAll()) {
                log.info(user.toString());
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