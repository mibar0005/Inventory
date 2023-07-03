package com.mibar.Inventory.repositories;

import com.mibar.Inventory.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest --> Annotation for a JPA test that focuses only on JPA components
//Using this annotation will disable full autoconfiguration and instead apply only
//configuration relevant to JPA test
//annotated with @DataJpaTest are transactional and roll back at the end of each test
@DataJpaTest
class BeerRepositoryTest {

    //Bring in your beerRepository interface
    @Autowired
    BeerRepository beerRepository;

    //Write a small test that checks that you can create a Beer
    @Test
    void testSavedBeer() {
        //Create a beer object using a builder. This Beer should only have a name field and
        //an ID field (automatically generated)
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("Purple Rain")
                .build());

        //Write an assertion to make sure that the beeer Object is not null
        assertThat(savedBeer).isNotNull();
        //write an assertion to make sure the beer object's id property is not null
        assertThat(savedBeer.getId()).isNotNull();

    }



}