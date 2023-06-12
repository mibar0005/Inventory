//package com.mibar.Inventory.controller;
//
//import org.springframework.http.ResponseEntity;
//
//
////We are using @ControllerAdvice so this can be picked up globally
//
////@ControllerAdvice
//public class ExceptionController {
//
////    (This was originally moved from the BeerController class)
//    //Create a method that returns a ResponseEntity for handleNotFoundException.
//    //To have this handled by the framework, We can annotate it with @ExceptionHandler
//    //If there is an exception in the controller it will be handled by this method
//    //All of you controllers will pick this up and use it
////    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException() {
//        //Return a new ResponseEntity (use a builder for this)
//        return ResponseEntity.notFound().build();
//    }
//
//}
