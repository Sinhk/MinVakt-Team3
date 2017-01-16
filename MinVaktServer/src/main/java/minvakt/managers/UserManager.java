package minvakt.managers;

import minvakt.datamodel.Employee;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by OlavH on 09-Jan-17.
 */
@Deprecated
public class UserManager {
    private static UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }
    //Singelton, do not modify
    private UserManager(){}

    private static SessionFactory factory;

    private List<Employee> employeeList = new ArrayList<>();

    @Deprecated // UserController
    public List<Employee> getEmployeeList() {

        /*Session session = factory.openSession();

        List<User> list = session.createQuery("from users").list();*/

        return employeeList;
    }

    @Deprecated // UserController
    public boolean addUser(Employee employee){
        Objects.requireNonNull(employee);

        return employeeList.add(employee);

    }
    @Deprecated // UserController
    public boolean removeUser(Employee employee){
        Objects.requireNonNull(employee);

        return employeeList.remove(employee);
    }

    @Deprecated // UserRepo
    public Optional<Employee> findUser(String email){
        Objects.requireNonNull(email);

        return employeeList.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();

    }

    /*public static void main(String[] args) {

        UserManager manager = UserManager.getInstance();

        manager.addUser(new User("olavh96@gmail.com",93240605,"Ostostost--",100));

        Optional<User> user = manager.findUser("olavH96@gmail.com");
        System.out.println(user);
        user.ifPresent(user1 -> user1.changePassword("Ostostost--","Matmatmat--"));

        user.ifPresent(user1 -> System.out.println(user1.authenticatePassword("Matmatmat--")));

    }*/
}
