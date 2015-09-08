package com.alex.thornburg.web.rest;

import com.alex.thornburg.web.rest.model.Address;
import com.alex.thornburg.web.rest.model.Family;
import com.alex.thornburg.web.rest.model.Kinship;
import com.alex.thornburg.web.rest.model.Person;
import com.alex.thornburg.web.rest.repo.AddressRepository;
import com.alex.thornburg.web.rest.repo.KinshipRepository;
import com.alex.thornburg.web.rest.repo.PersonRepository;
import com.alex.thornburg.web.rest.repo.SexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexthornburg on 9/7/15.
 */
@RestController
@RequestMapping("/kinship")
public class KinshipController {

    PersonRepository personRepository;
    SexRepository sexRepository;
    AddressRepository addressRepository;
    KinshipRepository kinshipRepository;

    @Autowired
    public KinshipController(PersonRepository personRepository,SexRepository sexRepository,
                             AddressRepository addressRepository,KinshipRepository kinshipRepository){
        this.personRepository = personRepository;
        this.sexRepository = sexRepository;
        this.addressRepository = addressRepository;
        this.kinshipRepository = kinshipRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Kinship kinship) {
        List<Person> people = new ArrayList<Person>();
        people.add(kinship.getOrigin());
        people.add(kinship.getRelative());
        saveTransientDependencies(people);
        Kinship result =kinshipRepository.save(kinship);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/{kinshipId}",method = RequestMethod.PUT)
    Kinship updateKinship(@RequestBody Kinship kinship,@PathVariable long kinshipId){
        Kinship newKin = kinshipRepository.findById(kinshipId);
        newKin.setDnaDifference(kinship.getDnaDifference());
        newKin.setOrigin(kinship.getOrigin());
        newKin.setRelative(kinship.getRelative());
        kinshipRepository.save(newKin);
        return newKin;
    }

    @RequestMapping(value = "/{kinshipId}",method = RequestMethod.GET)
    Kinship getKinship(@PathVariable long kinshipId){
        return kinshipRepository.findById(kinshipId);
    }

    public void saveTransientDependencies(List<Person> people){
        for(Person person:people){
            sexRepository.save(person.getSex());
            addressRepository.save(person.getAddress());
            personRepository.save(person);
        }
    }
}
