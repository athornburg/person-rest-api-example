package com.alex.thornburg.web.rest;

import com.alex.thornburg.web.rest.model.Person;
import com.alex.thornburg.web.rest.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Created by alexthornburg on 9/3/15.
 */
@RestController
@RequestMapping("/person")
class PersonEndpointController {

    private PersonRepository personRepository;

    @Autowired
    PersonEndpointController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Person person) {
        Person result = personRepository.save(new Person(person.getFirstName(), person.getLastName(), person.getAge()
                , person.getSex(), person.getEmail(), person.getPhoneNumber()
                , person.getAddress()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    Person getPerson(@PathVariable long personId) {
        return this.personRepository.findById(personId);
    }
    @RequestMapping(value = "/findByFirstName/{firstName}", method = RequestMethod.GET)
    Person getPerson(@PathVariable String firstName) {
        return this.personRepository.findByFirstName(firstName);
    }
}