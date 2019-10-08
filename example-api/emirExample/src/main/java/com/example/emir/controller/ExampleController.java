package com.example.emir.controller;

import com.example.emir.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emir")
public class ExampleController {

    @Autowired
    ExampleService exampleService;

    @GetMapping("/isOdd")
    public boolean isOddControl(@RequestHeader Integer sayi){
        return exampleService.isOdd(sayi);
    }
}
