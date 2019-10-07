package main.controllers;

import main.models.ExampleResponse;
import main.services.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ExampleController {

    @Autowired ExampleService exampleService;

    @CrossOrigin
    @GetMapping("/example")
    public ResponseEntity<ExampleResponse> exampleMethod(@RequestHeader int number){
        // return true if number is odd
        Boolean isOdd = exampleService.isOdd(number);
        ExampleResponse response = new ExampleResponse(isOdd, "Mahmut");
        return ResponseEntity.ok(response);
    }

}
