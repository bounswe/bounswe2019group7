package main.controllers;

import main.models.ExampleResponse;
import main.services.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ExampleController {

    @Autowired ExampleService exampleService;

    @GetMapping("/example")
    public ResponseEntity<ExampleResponse> exampleMethod(@RequestHeader int number){
        // return true if number is odd
        Boolean isOdd = exampleService.isOdd(number);
        ExampleResponse response = new ExampleResponse(isOdd, "Mahmut");
        return ResponseEntity.ok(response);
    }

}
