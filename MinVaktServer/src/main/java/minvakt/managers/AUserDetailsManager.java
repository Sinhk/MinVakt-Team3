package minvakt.managers;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.util.Assert;

/**
 * Created by sindr on 18.01.2017.
 * in project: MinVakt-Team3
 */
public class AUserDetailsManager extends JdbcUserDetailsManager {
    String usersByUsernameQuery = "select email,passwd,enabled from employee where email=?";
    String authoritiesByUsernameQuery = "SELECT email, IF(admin,'ADMIN','USER') from employee e Left JOIN employee_category ec ON e.category_id = ec.category_id WHERE email =?;";
    String changePasswordSql = "UPDATE employee SET passwd = ? WHERE email = ?";
    String createUserSql = "INSERT INTO employee (email,passwd,enabled) VALUES (?,?,?)";
    String updateUserSql = "UPDATE employee SET passwd = ?, enabled = ? WHERE email = ?";
    String userExistsSql = "SELECT email FROM employee WHERE email= ?";


    @Override
    public void createUser(UserDetails user) {
        user = new User(user.getUsername(), encrypt(user.getPassword()), user.getAuthorities());
        super.createUser(user);
    }
    private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
       Assert.hasText(user.getPassword(), "Password may not be empty or null");
    }
    @Override
    public void updateUser(UserDetails user) {
        validateUserDetails(user);
        String passEncrypt = encrypt(user.getPassword());
        getJdbcTemplate().update(updateUserSql, ps -> {
            ps.setString(1, passEncrypt);
            ps.setBoolean(2, user.isEnabled());
            ps.setString(3, user.getUsername());
        });
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        newPassword = encrypt(newPassword);
        super.changePassword(oldPassword, newPassword);
    }

    private String encrypt(String toEncrypt) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(toEncrypt);
    }
}
