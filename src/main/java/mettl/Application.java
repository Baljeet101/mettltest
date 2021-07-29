package mettl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Properties;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    private static final Class<Application> app = Application.class;
    public static void main(String[] args) {
        System.setProperty("spring.config.location","classpath:Application/");
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(app)
                .properties(getProperties());
    }

    private static Properties getProperties() {
        final Properties properties = new Properties();
        properties.put("spring.config.location","classpath:Application/");
        return properties;
    }
}
