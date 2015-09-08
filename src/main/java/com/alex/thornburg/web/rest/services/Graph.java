package com.alex.thornburg.web.rest.services;

import com.alex.thornburg.web.rest.model.Kinship;
import com.alex.thornburg.web.rest.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexthornburg on 9/7/15.
 */
public class Graph {
    private List<Kinship> adjacencyList;

    public Graph(List<Kinship> adjacencyList){
        this.adjacencyList = adjacencyList;
    }

    public List<Kinship> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(List<Kinship> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }


    public List<Person> getAllKinships(){
        List<Person> allKinships = new ArrayList<>();
        for(Kinship e:adjacencyList){
            if(!allKinships.contains(e.getOrigin())){
                allKinships.add(e.getOrigin());
            }
            if(!allKinships.contains(e.getRelative())){
                allKinships.add(e.getRelative());
            }

        }
        return allKinships;
    }

    public List<Person> getNeighbors(Person start){
        List<Person> output = new ArrayList<>();
        for(Kinship e:adjacencyList){
            if(e.getOrigin().getId()==start.getId()){
                output.add(e.getRelative());
            }
        }
        return output;
    }

    public double getDistanceBetweenKinships(Person start,Person end){
        for(Kinship e:adjacencyList){
            if(e.getOrigin().equals(start) && e.getRelative().equals(end)){
                return e.getDnaDifference();
            }
        }
        return Double.parseDouble(null);
    }

}
