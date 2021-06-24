package booking.hotel;


import booking.hotel.beans.ApplicationBeans;
import booking.hotel.beans.PersistenceBeanConfiguration;
import booking.hotel.beans.SecurityConfig;
import booking.hotel.beans.SwaggerConfig;
import booking.hotel.security.configuration.WebSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "booking.hotel")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
@EnableSwagger2

@Import({
        ApplicationBeans.class,
        WebSecurityConfiguration.class,
        PersistenceBeanConfiguration.class,
        SwaggerConfig.class,
        SecurityConfig.class})

public class SpringBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarter.class, args);
    }
}