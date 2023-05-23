package com.mibar.Inventory.controller;

import com.mibar.Inventory.model.Beer;
import com.mibar.Inventory.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;


    //Create a Patch method that updates part of an object
    //This should return a ResponseEntity of No Content
    //Use the @PathVariable and @RequestBody annotations
    @PatchMapping("{beerId}")
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        beerService.patchBeerById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //Create a delete method that searched for a beer by Id and removes it
    //This method should return a ResponseEntity
    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
        beerService.deleteById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //Create a Put method that returns a ResponseEntity
    //Pass in the beerId and the beer object
    @PutMapping("{beerId}")
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        beerService.updateBeerById(beerId, beer);
        //return a ResponseEntity of no content
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    //Create a POST method that takes in a Beer object and returns a ResponseEntity
//    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);

        //Create a header object that will return the Location of the newly created API
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

//    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    @GetMapping("{beerId}")
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get Beer by Id");
        return beerService.getBeerById(beerId);
    }

}
