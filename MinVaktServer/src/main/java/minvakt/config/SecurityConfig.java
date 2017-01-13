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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   /* @Autowired
    private DataSource dataSource;*/

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user@minvakt.no").password("user").roles("USER")
                .and().withUser("admin@minvakt.no").password("admin").roles("ADMIN", "USER");
        /*auth
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