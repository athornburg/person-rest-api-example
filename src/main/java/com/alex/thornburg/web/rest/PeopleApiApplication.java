package com.alex.thornburg.web.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PeopleApiApplication {

//    @Bean
//    CommandLineRunner init(PersonRepository personRepository) {
//        return (evt) -> personRepository.save(new Person("Test","Person",57,new Sex(true,false),"testperson1@gmail.com","740-526-6225",new Address("555 North West","United States","Kanye Ave.",60606)));
//    }

    public static void main(String[] args) {
        SpringApplication.run(PeopleApiApplication.class, args);
    }
}

