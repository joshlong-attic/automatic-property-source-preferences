package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {

            String property = environment.getProperty("a.b");
            System.out.println("property: " + property);

        };
    }

    @Bean
    SecurityWebFilterChain config (ServerHttpSecurity http) {

//        http.cors( c -> c.configurationSource( ))
        return http
//                .headers( hs -> hs.)
                .build() ;
    }
}

class JoshsEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            Resource resource = new ClassPathResource("/my.yaml");
            YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
            List<PropertySource<?>> propertySources = yamlPropertySourceLoader.load("my-custom-yaml", resource);
//            MapPropertySource mapPropertySource = new MapPropertySource("joshMapPropertySource" , Map.of("a.b" , "hi")) ;
            for (PropertySource<?> propertySource : propertySources) {
                environment.getPropertySources().addFirst(propertySource);
            }
        } //
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}