package com.eyetrade.backend;

import com.eyetrade.backend.service.EventRssReaderService;
import com.sun.syndication.io.FeedException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by Emir Gökdemir
 * on 8 Kas 2019
 */
@SpringBootTest
public class EventTests {

    @Spy
    @InjectMocks
    public EventRssReaderService readerService;

    @Test
    public void RssEventReaderTest() throws FeedException {
        //examined in debug mode
        readerService.readAndSaveFeed();
    }
}
