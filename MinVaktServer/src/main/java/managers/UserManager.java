package managers;

import datamodel.User;

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

    private UserManager() {
    }

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

        return userList.stream().filter(user -> user.getEmail().equals(email)).findFirst();

    }

    public boolean changePasswordForUser(User user, String oldPass, String newPass){

        Objects.requireNonNull(user);

        return user.changePassword(oldPass, newPass);
    }


}
