package com.alex.thornburg.web.rest.services;

import com.alex.thornburg.web.rest.model.Person;

import java.util.*;

/**
 * Created by alexthornburg on 9/7/15.
 * This is an interesting programming example
 * to show the advantages of storing a family
 * as a graph.
 */
public class GenePathFinder {
    private Graph g;
    Map<Person,Double> distance;
    Map<Person,Double> prev;

    public GenePathFinder(Graph g){
        this.g = g;
        distance = new HashMap<>();
        prev = new HashMap<>();
    }

    public Map<Person,Double> lazy(Person start){
        Queue<Person> q = new PriorityQueue<>();
        for(Person n:g.getAllKinships()){
            q.add(n);
            distance.put(n,Double.MAX_VALUE);
            prev.put(n,null);
        }
        distance.put(start, 0.0);
        while(!q.isEmpty()){
            Person u = findMinNode(q);
            q.remove(u);
            List<Person> people = g.getNeighbors(u);
            for(Person v:people){
                double temp = distance.get(u)+g.getDistanceBetweenKinships(u,v);
                if(temp<distance.get(v)){
                    distance.put(v, temp);
                    prev.put(v, distance.get(u));
                }
            }
        }
        return distance;
    }

    private Person findMinNode(Queue<Person> q){
        Person n;
        Person smallest = null;
        Iterator<Person> it = q.iterator();
        int count = 0;
        while(it.hasNext()){
            n = it.next();
            if(count==0){
                smallest = n;
            }
            if(distance.get(n)<distance.get(smallest)){
                smallest = n;
            }
        }
        return smallest;
    }
}
