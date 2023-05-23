package com.mibar.Inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mibar.Inventory.model.Beer;
import com.mibar.Inventory.service.BeerService;
import com.mibar.Inventory.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//We want to bring the WebMvcTest to indicate that this will be a test splice specifically to the BeerController class
@WebMvcTest(BeerController.class)
class BeerControllerTest {

//    @Autowired
//    BeerController beerController;

    //Bring the MockMvc class and set it to @Autowired
    //This will require the BeerService context
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    //Bring in the BeerService
    //Use the @MockBean annotation, this tells Mockito to provide a Mock of this into the Spring Context
    //Without the MockBean we would be an exception. This adds the service as a Mockito Mock
    @MockBean
    BeerService beerService;

    //We can set up Mockito to return back data
    BeerServiceImpl beerServiceImpl;

    //Create a @BeforeEach method and initialize the BeerServiceImpl since we will be
    //manipulating this in upcoming tests
    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }


    //For the PUT request we are updating the service...it doesn't return anything so you will at least
    //want to verify() that the method is being called
    @Test
    void testUpdateBeer() throws Exception {
        //Grab the first beer of the map
        Beer beer = beerServiceImpl.listBeers().get(0);

        //we do not have a given(), so we can just get straight to the mocking
        mockMvc.perform(put("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        //We want to verify the interaction. We want to verify that this has one interaction
        //We want to verify that the beerService updateBeerById method is being called.
        //We can pass any() UUID. class and expect to return any() Beer.class
        verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));

    }

    @Test
    void testCreateNewBeer() throws Exception {
        //WE MOVED THIS CODE TO THE TOP AND AUTOWIRED IT
        //Use an objectMapper in order to serialize and deserialize POJOS into JSON and vice versa
        // ObjectMapper objectMapper = new ObjectMapper();
        //We need to do some configuration on our objectMapper in order for it to find and register Modules
        //in order to handle the DateTime type
//        objectMapper.findAndRegisterModules();
        //Get the first beer from the listBeer()
        Beer beer = beerServiceImpl.listBeers().get(0);
        //The beer Object should not have a version property and an ID property
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }



    @Test
    void getBeerById() throws Exception {
        //Create a new Beer by using the beerServiceImpl to grab a beer from the list
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        //use the given() to tell mockito that when give any beer with any id will return the test beer
        //In this line we are configuring mockito to go ahead and return that test beer object
        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        //You may have to look the get() for MockMvcRequestBuilders or import it manually
        //Explanation: We are telling MockMvc that we want to perform a GET against the urlTemplate
        //and we should get back an Ok status
        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Here we are expecting a response of type JSOn
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //Here we will be using JSON matchers and use some assertions.
                //We need to use the jsonPath() and give it an expression. They all start with "$." (from the root)
                //Even if the test fails, we are still going to get a JSON object as a response
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    //Create a Mock method that will test for listBeers
    @Test
    void testListBeers() throws Exception {
        //Work with mockito to return a list of beers
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());
        //We can then perform a mockMvc and perform a GET request against our url
        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //Make an assertion using json path that this list will be at least of size 3
                .andExpect(jsonPath("$.length()", is(3)));
    }
}