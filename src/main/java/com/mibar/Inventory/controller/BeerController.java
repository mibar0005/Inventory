package com.mibar.Inventory.controller;

import com.mibar.Inventory.model.Beer;
import com.mibar.Inventory.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    //Create a POST method that takes in a Beer object and returns a ResponseEntity
//    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get Beer by Id");
        return beerService.getBeerById(beerId);
    }

}
