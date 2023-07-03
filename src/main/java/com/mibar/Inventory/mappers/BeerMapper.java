package com.mibar.Inventory.mappers;

import com.mibar.Inventory.entities.Beer;
import com.mibar.Inventory.model.BeerDTO;
import org.mapstruct.Mapper;

//Tels in mapping objects from one model to another
@Mapper
public interface BeerMapper {

    //Declare a method that returns an object of type Beer, but accepts
    //an BeerDTO object as a parameter.
    Beer beerDtoToBeer(BeerDTO dto);

    //Declare a method that returns an object of Type BeerDTO, but accepts a
    //Beer object
    BeerDTO beerToBeerDto(Beer beer);
}
