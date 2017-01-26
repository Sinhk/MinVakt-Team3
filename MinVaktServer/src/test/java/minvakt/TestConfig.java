package minvakt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by magnu on 23.01.2017.
 */
@Configuration
@ComponentScan("minvakt")
public class TestConfig {


/*    @Bean
    static public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db.sql")
                .build();
    }

    @Bean
    static public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/
}