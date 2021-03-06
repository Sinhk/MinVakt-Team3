package minvakt.config;

import minvakt.managers.AUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final AuthFailureHandler authFailureHandler;

    private final AuthSuccessHandler authSuccessHandler;

    @Autowired
    public SecurityConfig(DataSource dataSource, AuthFailureHandler authFailureHandler, AuthSuccessHandler authSuccessHandler) {
        this.dataSource = dataSource;
        this.authFailureHandler = authFailureHandler;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager manager = new AUserDetailsManager();
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
    }

    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        http
                .addFilterBefore(filter, CsrfFilter.class)
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/users/**/getNewPassword").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/do_logout")
                .logoutSuccessUrl("/login?logout")
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

@Component
class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {


        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("{\"success\": true}");
        response.getWriter().flush();
    }
}

@Component
class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print("{\"success\": false}");
        response.getWriter().flush();
    }
}
