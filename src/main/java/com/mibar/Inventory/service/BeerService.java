package com.mibar.Inventory.service;


import com.mibar.Inventory.model.Beer;

import java.util.List;
import java.util.UUID;

//Create an Interface for the BeerService and add the Service methods here
public interface BeerService {

    //Create a method that will return a list of all beers
    List<Beer> listBeers();

    //Create a method that returns a beer by ID
    public Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);
}
