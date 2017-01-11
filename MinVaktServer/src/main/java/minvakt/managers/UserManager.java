package minvakt.managers;

import minvakt.datamodel.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class UserManager {
    private static UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }
    //Singelton, do not modify
    private UserManager(){}


    private List<User> userList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }

    public boolean addUser(User user){
        Objects.requireNonNull(user);

        return userList.add(user);

    }
    public boolean removeUser(User user){
        Objects.requireNonNull(user);

        return userList.remove(user);
    }

    public Optional<User> findUser(String email){
        Objects.requireNonNull(email);

        return userList.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();

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
