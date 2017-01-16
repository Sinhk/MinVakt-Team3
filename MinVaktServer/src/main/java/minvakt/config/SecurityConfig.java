package minvakt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    // TODO: 15.01.2017 Set query to match database
    public JdbcUserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        manager.setUsersByUsernameQuery(
                "select email,passwd,enabled from employee where email=?");
        manager.setAuthoritiesByUsernameQuery(
                "SELECT email, IF(admin,'ADMIN','USER') from employee e Left JOIN employee_category ec ON e.category_id = ec.category_id WHERE email =?;");
        manager.setChangePasswordSql("UPDATE employee SET passwd = ? WHERE email = ?");
        manager.setCreateUserSql("INSERT INTO employee (email,passwd,enabled) VALUES (?,?,?)");
        manager.setUpdateUserSql("UPDATE employee SET passwd = ?, enabled = ? WHERE email = ?");
        manager.setUserExistsSql("SELECT email FROM employee WHERE email= ?");
        manager.setRolePrefix("ROLE_");
        return manager;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsManager()).passwordEncoder(passwordEncoder());
        /*auth
                .inMemoryAuthentication()
                .withUser("user@minvakt.no").password("user").roles("USER")
                .and().withUser("admin@minvakt.no").password("admin").roles("ADMIN", "USER");
        *//*auth
                .jdbcAuthentication()
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from user_roles where username=?");*/
    }

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .anyRequest();
    }*/

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/do_logout")
                .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}