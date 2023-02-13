package com.chenfj;

import com.chenfj.config.properties.JdbcProperties;
import com.chenfj.security.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootTemplateApplication {

    @Autowired
    private JdbcProperties jdbcProperties;
    @Autowired
    private JwtProperties jwtProperties;

    public static void main(String[] args) {
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("");
        try {
            /*String env = "prod";
            // Some sanity checks on `env`
            new SpringApplicationBuilder(SpringbootTemplateApplication.class).profiles(env).run(args);*/
            ConfigurableApplicationContext run = SpringApplication.run(SpringbootTemplateApplication.class, args);
            System.out.println("");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
