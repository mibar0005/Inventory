package com.mibar.Inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mibar.Inventory.model.Beer;
import com.mibar.Inventory.service.BeerService;
import com.mibar.Inventory.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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

    //Refactor the ArgumentCapture and declare it as a field here rather than inside of a method.
    //Use the @Captor bean
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    //Create a Captor for the Beer object as well, this will give us a reusable component
    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    //Create a @BeforeEach method and initialize the BeerServiceImpl since we will be
    //manipulating this in upcoming tests
    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }


    @Test
    void testPatchBeerById() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        //We can create a Map<String, Object> and we can put the key (JSON property) and the value
        //This mimics what the client would do
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        //We can write out mockMvc properties
        //Instead of manually writing the urlTemplate we can call the BeerController and bring in the BEER_PATH
        mockMvc.perform(patch(BeerController.BEER_PATH + "/" + beer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        //Write a verify method that will take in the Captors of uuid and beer
        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }


    //For the delete method we will also be using a verify() method
    //We will also be using ArgumentCaptors to capture a property
    @Test
    void testDeleteBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        //We are going to refactor from BeerController.BEER_PATH + "/" + beer.getId() to...
        //BeerController.BEER_PATH_ID, beer.getId();  --> URI properties
        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //We want to make sure that the beer.getId() property that we are getting is being parsed
        //and sent into the deleteById() method on Beer service.
        // {Refactored in the Field Declaration Above!!!}
        //ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        //We use the capture() method to capture everything that is being passed here
        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        //We can then use an Assertion  to make sure that the beerId is Equals to the value being
        //of the uuidArgumentCaptor.capture() value
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }


    //For the PUT request we are updating the service...it doesn't return anything so you will at least
    //want to verify() that the method is being called
    @Test
    void testUpdateBeer() throws Exception {
        //Grab the first beer of the map
        Beer beer = beerServiceImpl.listBeers().get(0);

        //we do not have a given(), so we can just get straight to the mocking
        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
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

        mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    //We are going to create a test that throws and exception with Mockito.
    //Create a method that tests for when a get beer by id is not found
    @Test
    void getBeerByIdNotFound_ExceptionHandling() throws Exception {
        //Set up out given() method with any() UUID, will throw NotFoundException class
        given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);

        //Create a mockMvc get method with the call the beerController with the
        //BEER_PATH_ID and a random UUID. You should expect a status of isNotFound
        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());

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
        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getId())
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
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //Make an assertion using json path that this list will be at least of size 3
                .andExpect(jsonPath("$.length()", is(3)));
    }
}