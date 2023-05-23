package com.mibar.Inventory.controller;

import com.mibar.Inventory.model.Beer;
import com.mibar.Inventory.service.BeerService;
import com.mibar.Inventory.service.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

    //Bring in the BeerService
    //Use the @MockBean annotation, this tells Mockito to provide a Mock of this into the Spring Context
    //Without the MockBean we would be an exception. This adds the service as a Mockito Mock
    @MockBean
    BeerService beerService;

    //We can set up Mockito to return back data
    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

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
}