package com.alex.thornburg.web.rest;

import com.alex.thornburg.web.rest.model.Person;
import com.alex.thornburg.web.rest.repo.AddressRepository;
import com.alex.thornburg.web.rest.repo.PersonRepository;
import com.alex.thornburg.web.rest.repo.SexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * Created by alexthornburg on 9/3/15.
 */
@RestController
@RequestMapping("/person")
class PersonEndpointController {

    private PersonRepository personRepository;
    private SexRepository sexRepository;
    private AddressRepository addressRepository;

    @Autowired
    PersonEndpointController(PersonRepository personRepository,SexRepository sexRepository,AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.sexRepository = sexRepository;
        this.addressRepository = addressRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Person person) {
        sexRepository.save(person.getSex());
        addressRepository.save(person.getAddress());
        Person result = personRepository.save(new Person(person.getFirstName(), person.getLastName(), person.getAge()
                , person.getSex(), person.getEmail(), person.getPhoneNumber()
                , person.getAddress()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{personId}",method = RequestMethod.DELETE)
    void deletePerson(@PathVariable long personId) {
        personRepository.delete(personId);
    }

    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    Person getPerson(@PathVariable long personId) {
        return this.personRepository.findById(personId);
    }

    @RequestMapping(value = "/{personId}",method = RequestMethod.PUT)
    Person updatePerson(@RequestBody Person updatedPerson,@PathVariable long personId) {
        Person old = personRepository.findById(personId);
        addressRepository.save(updatedPerson.getAddress());
        old.setAddress(updatedPerson.getAddress());
        old.setFirstName(updatedPerson.getFirstName());
        old.setLastName(updatedPerson.getLastName());
        old.setEmail(updatedPerson.getEmail());
        old.setPhoneNumber(updatedPerson.getPhoneNumber());
        old.setAge(updatedPerson.getAge());
        sexRepository.save(updatedPerson.getSex());
        old.setSex(updatedPerson.getSex());
        personRepository.save(old);
        return old;
    }

    @RequestMapping(value = "/findByFirstName/{firstName}", method = RequestMethod.GET)
    List<Person> getPerson(@PathVariable String firstName) {
        return this.personRepository.findByFirstName(firstName);
    }




}
