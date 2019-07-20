package com.valychbreak.mymedialib;

import com.valychbreak.mymedialib.utils.ApplicationFirstRunSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * Created by valych on 2/25/17.
 */
@SpringBootApplication
//@EnableAutoConfiguration(exclude = { JacksonAutoConfiguration.class })
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ApplicationFirstRunSetup applicationFirstRunSetup;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);


        //printAllBeans(ctx);
    }

    /*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
            }
        };
    }*/

    private static void printAllBeans(ApplicationContext ctx) {
        log.info("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            log.info(beanName);
        }
    }

    /*@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            applicationFirstRunSetup.execute();
        };
    }*/
}
