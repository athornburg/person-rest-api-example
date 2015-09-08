package com.alex.thornburg.web.rest;

import com.alex.thornburg.web.rest.model.*;
import com.alex.thornburg.web.rest.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PeopleApiApplication {

    @Bean
    CommandLineRunner init(PersonRepository personRepository,
                           AddressRepository addressRepository,
                           SexRepository sexRepository,
                           FamilyRepository familyRepository,
                           KinshipRepository kinshipRepository) {
        Sex sex = new Sex(true,false);
        sexRepository.save(sex);
        Address address = new Address("555 North West","United States","Kanye Ave.",60606);
        addressRepository.save(address);
        Person son = new Person("Test","Person",57,sex,"testperson1@gmail.com","740-526-6225",address);
        personRepository.save(son);
        Person father = new Person("Test2","Person",88,sex,"testperson1@gmail.com","740-526-6225",address);
        personRepository.save(father);
        Kinship fatherSon = new Kinship("Father",father,son,0.23);
        kinshipRepository.save(fatherSon);
        List<Kinship> kinships = new ArrayList<>();
        kinships.add(fatherSon);
        return (evt) -> familyRepository.save(
                new Family("Person",kinships));
    }

    public static void main(String[] args) {
        SpringApplication.run(PeopleApiApplication.class, args);
    }
}

