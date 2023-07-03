package com.mibar.Inventory.service;


import com.mibar.Inventory.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Create an Interface for the BeerService and add the Service methods here
public interface BeerService {

    //Create a method that will return a list of all beers
    List<BeerDTO> listBeers();

    //Create a method that returns a beer by ID
    //Use a Java Optional
    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    void updateBeerById(UUID beerId, BeerDTO beer);

    void deleteById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
