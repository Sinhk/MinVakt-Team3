package minvakt.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private AuthenticationManager authenticationManager;
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
        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context "
                            + "for current user.");
        }

        String username = currentUser.getName();

        // If an authentication manager has been set, re-authenticate the user with the
        // supplied password.
        if (authenticationManager != null) {
            logger.info("Reauthenticating user '" + username
                    + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, oldPassword));
        } else {
            logger.info("No authentication manager set. Password won't be re-checked.");
        }

        logger.debug("Changing password for user '" + username + "'");

        getJdbcTemplate().update(changePasswordSql, newPassword, username);
    }

    private String encrypt(String toEncrypt) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(toEncrypt);
    }
}
