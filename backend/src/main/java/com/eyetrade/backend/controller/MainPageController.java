package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.resource.mainpage.MainPageResource;
import com.eyetrade.backend.service.MainPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main_page")
@Api(value = "MainPage", tags = {"Main Page"})
public class MainPageController {

    @Autowired
    private MainPageService mainPageService;

    @ApiOperation(value = "Get the main page feed", response = MainPageResource.class)
    @GetMapping("/get_feed")
    public ResponseEntity<MainPageResource> getMainPageFeed(){
        MainPageResource resource = mainPageService.getMainPageFeed();
        return ResponseEntity.ok(resource);
    }

}
