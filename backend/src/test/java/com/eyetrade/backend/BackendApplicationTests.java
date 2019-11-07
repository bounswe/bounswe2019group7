package com.eyetrade.backend;

import com.eyetrade.backend.service.RssEventReaderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Spy
	@InjectMocks
	public RssEventReaderService readerService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void RssEventReaderTest(){
		//examined in debug mode
		readerService.readFeed();
	}
}
