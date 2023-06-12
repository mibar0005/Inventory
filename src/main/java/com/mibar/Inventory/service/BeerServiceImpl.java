package com.mibar.Inventory.service;

import com.mibar.Inventory.model.Beer;
import com.mibar.Inventory.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    //Create a Map and to store the UUID and Beer name.
    public Map<UUID, Beer> beerMap;

    //Since we are not currently working with a database, we will be creating a three beer
    //instances in here.
    public BeerServiceImpl() {
        //Initialize your beer HashMap
        this.beerMap = new HashMap<>();

        //Create three beer instances
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Riverside Rachets")
                .beerStyle(BeerStyle.SOUR)
                .upc("9876543")
                .price(new BigDecimal("15.99"))
                .quantityOnHand(58)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("The Can Can")
                .beerStyle(BeerStyle.DOUBLE_IPA)
                .upc("1346798264")
                .price(new BigDecimal("21.99"))
                .quantityOnHand(13)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        //Add the beers into the beerMap
        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }


    //Create a method that will return a list of all beers
    @Override
    public List<Beer> listBeers() {
        //Use the Map method in order to retrieve all values
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<Beer> getBeerById(UUID id) {

        log.debug("Get Beer by Id - in service. Id: " + id.toString());
        //Use the Map method's in order to get Beer object by passing in the id (Key) value
        //Since we are now using an Optional<Beer> we have to modify this to return
        //Optional.of(beerMap.get(id));
        return Optional.of(beerMap.get(id));
//        return beerMap.get(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {

        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .version(beer.getVersion())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    @Override
    public void updateBeerById(UUID beerId, Beer beer) {
        Beer existing = beerMap.get(beerId);
        existing.setBeerName(beer.getBeerName());
        existing.setPrice(beer.getPrice());
        existing.setUpc(beer.getUpc());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);
    }


    //The rule of thumb when doing a Patch is....
    //If the property is null, then you don't do an update
    @Override
    public void patchBeerById(UUID beerId, Beer beer) {
        Beer existing = beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName())) {
            existing.setBeerName(beer.getBeerName());
        }

        if (beer.getBeerStyle() != null) {
            existing.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            existing.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            existing.setUpc(beer.getUpc());
        }
    }
}
