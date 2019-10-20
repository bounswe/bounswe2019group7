package main.services;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public boolean isOdd(int number){
        return number%2 == 1;
    }

}
