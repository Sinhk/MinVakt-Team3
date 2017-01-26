package minvakt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan
public class SpringWebConfig
        extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private final Environment environment;

    private ApplicationContext applicationContext;


    @Autowired
    public SpringWebConfig(Environment environment) {
        super();
        this.environment = environment;
    }


    public void setApplicationContext(final ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof org.springframework.http.converter.json.MappingJackson2HttpMessageConverter) {
                ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
                mapper
                        .registerModule(new Hibernate5Module())
                        .registerModule(new ParameterNamesModule())
                        .registerModule(new Jdk8Module())
                        .registerModule(new JavaTimeModule())
                        //.setDateFormat()
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            }
        }
    }


    /* ******************************************************************* */
    /*  GENERAL CONFIGURATION ARTIFACTS                                    */
    /*  Static Resources, i18n Messages, Formatters (Conversion Service)   */
    /* ******************************************************************* */

    /*
     *  Dispatcher configuration for serving static resources
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/images/**").addResourceLocations("/static/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");
        registry.addResourceHandler("/**").addResourceLocations("/static/");
    }

    /*
     *  Message externalization/internationalization
     */
  /*  @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("Messages");
        return messageSource;
    }*/

    /*
     * Add formatter for class {@link thymeleafexamples.stsm.business.entities.Variety}
     * and {@link java.util.Date} in addition to the one registered by default
     */
    /*@Override
    public void addFormatters(final FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatter(varietyFormatter());
        registry.addFormatter(dateFormatter());
    }*/

    /*@Bean
    public VarietyFormatter varietyFormatter() {
        return new VarietyFormatter();
    }

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }*/


    /* **************************************************************** */
    /*  THYMELEAF-SPECIFIC ARTIFACTS                                    */
    /*  TemplateResolver <- TemplateEngine <- ViewResolver              */
    /* **************************************************************** */

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode(TemplateMode.HTML);
        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev")))) {
            templateResolver.setCacheable(false);
        }
        return templateResolver;
    }

    @Bean
    public UrlTemplateResolver urlTemplateResolver() {
        return new UrlTemplateResolver();
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(templateResolver());
        templateEngine.addTemplateResolver(urlTemplateResolver());
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev")))) {
            viewResolver.setCache(false);
        }
        return viewResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("homepage");
        registry.addViewController("/ansatte").setViewName("employee");
        registry.addViewController("/ansatte/ny").setViewName("newUser");
        registry.addViewController("/vaktliste").setViewName("vaktliste");
        registry.addViewController("/teststuff").setViewName("index");
        registry.addViewController("/tilgjengelighet").setViewName("availability");
        registry.addViewController("/changeShift").setViewName("changeShift");
        registry.addViewController("/absence").setViewName("absence");
        registry.addViewController("/endreBruker").setViewName("changeUser");
        registry.addViewController("/endrePassord").setViewName("changePassword");
        registry.addViewController("/notification").setViewName("notification");
        registry.addViewController("/makeSheet").setViewName("makeSheet");
        registry.addViewController("/shiftChange").setViewName("shiftChange");
        registry.addViewController("/endreBrukerAdmin").setViewName("changeUserAdmin");
        registry.addViewController("/ledigeVakter").setViewName("availableShifts");
        registry.addViewController("/employeeE").setViewName("employeeE");
        registry.addViewController("/help").setViewName("help");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
