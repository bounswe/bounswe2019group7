package com.example.emir.service;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private boolean isEven(Integer sayi)
    {
        return sayi%2==0;
    }

    public boolean isOdd(Integer sayi)
    {
        return !isEven(sayi);
    }
}
