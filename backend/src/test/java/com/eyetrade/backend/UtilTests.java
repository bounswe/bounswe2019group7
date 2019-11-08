package com.eyetrade.backend;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

import static com.eyetrade.backend.utils.DateUtils.TimeFormatter;


/**
 * Created by Emir Gökdemir
 * on 8 Kas 2019
 */
@SpringBootTest
public class UtilTests {

    @Test
    public void UuidGenerateTest(){
        Assert.assertEquals((UUID.fromString("3edc1ffb-ce7d-4602-809d-fada541abf2d")).toString(),"3edc1ffb-ce7d-4602-809d-fada541abf2d");
    }

    @Test
    public void TimeFormatterTest(){
        Assert.assertEquals(TimeFormatter(new Date(),"yyyy"),"2019");
        System.out.println();
        System.out.println("\n"+new Date());
    }
}
