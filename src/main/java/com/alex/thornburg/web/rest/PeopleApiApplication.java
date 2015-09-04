package com.alex.thornburg.web.rest;

import com.alex.thornburg.web.rest.model.Address;
import com.alex.thornburg.web.rest.model.Person;
import com.alex.thornburg.web.rest.model.Sex;
import com.alex.thornburg.web.rest.repo.AddressRepository;
import com.alex.thornburg.web.rest.repo.PersonRepository;
import com.alex.thornburg.web.rest.repo.SexRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PeopleApiApplication {

    @Bean
    CommandLineRunner init(PersonRepository personRepository,
                           AddressRepository addressRepository,
                           SexRepository sexRepository) {
        Sex sex = new Sex(true,false);
        sexRepository.save(sex);
        Address address = new Address("555 North West","United States","Kanye Ave.",60606);
        addressRepository.save(address);
        return (evt) -> personRepository.save(
                new Person("Test","Person",57,sex,"testperson1@gmail.com","740-526-6225",address));
    }

    public static void main(String[] args) {
        SpringApplication.run(PeopleApiApplication.class, args);
    }
}

