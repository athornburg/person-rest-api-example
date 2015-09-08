package com.alex.thornburg.web.rest;

import com.alex.thornburg.web.rest.model.Family;
import com.alex.thornburg.web.rest.model.Kinship;
import com.alex.thornburg.web.rest.model.Person;
import com.alex.thornburg.web.rest.repo.*;
import com.alex.thornburg.web.rest.services.GenePathFinder;
import com.alex.thornburg.web.rest.services.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created by alexthornburg on 9/6/15.
 */
@RestController
@RequestMapping("/family")
public class FamilyController {
    private FamilyRepository familyRepository;
    private PersonRepository personRepository;
    private SexRepository sexRepository;
    private AddressRepository addressRepository;
    private KinshipRepository kinshipRepository;

    @Autowired
    public FamilyController(FamilyRepository familyRepository,PersonRepository personRepository,SexRepository sexRepository,AddressRepository addressRepository,KinshipRepository kinshipRepository){
        this.familyRepository = familyRepository;
        this.personRepository = personRepository;
        this.sexRepository = sexRepository;
        this.addressRepository = addressRepository;
        this.kinshipRepository = kinshipRepository;
    }


    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Family family) {
        saveTransientDependencies(family.getPeople());
        Family result =familyRepository.save(family);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{familyId}",method = RequestMethod.DELETE)
    void deleteFamily(@PathVariable long familyId){
        familyRepository.delete(familyId);
    }

    @RequestMapping(value = "/{familyId}",method = RequestMethod.PUT)
    Family updateFamily(@RequestBody Family updatedFamily,@PathVariable long familyId) {
        Family newFam = familyRepository.findById(familyId);
        newFam.setPeople(updatedFamily.getPeople());
        newFam.setSurname(updatedFamily.getSurname());
        saveTransientDependencies(newFam.getPeople());
        familyRepository.save(newFam);
        return newFam;
    }

    @RequestMapping(value="findBySurname/{surname}",method=RequestMethod.GET)
    Family getFam(@PathVariable String surname){
        return this.familyRepository.findBySurname(surname);
    }

    @RequestMapping(value="/{familyId}",method=RequestMethod.GET)
    Family getFamById(@PathVariable long familyId){
        return this.familyRepository.findById(familyId);
    }


    //Just demonstrating the power of handling a family this way.
    @RequestMapping(value="findShortestGeneVarience/{familyId}/{startPersonId}",method=RequestMethod.GET)
    Map<Person,Double> getShortestGeneVariance(@PathVariable long startPersonId,@PathVariable long familyId){
        List<Kinship> kinships = familyRepository.findById(familyId).getPeople();
        Graph g = new Graph(kinships);
        GenePathFinder pathFinder = new GenePathFinder(g);
        return pathFinder.lazy(personRepository.findById(startPersonId));
    }


    public void saveTransientDependencies(List<Kinship> people){
        for(Kinship person:people){
            sexRepository.save(person.getOrigin().getSex());
            sexRepository.save(person.getRelative().getSex());
            addressRepository.save(person.getOrigin().getAddress());
            addressRepository.save(person.getRelative().getAddress());
            personRepository.save(person.getOrigin());
            personRepository.save(person.getRelative());
            kinshipRepository.save(person);
        }
    }


}
