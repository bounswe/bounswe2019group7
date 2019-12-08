package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.AnnotationDto;
import com.eyetrade.backend.model.resource.annotation.AnnotationResource;
import com.eyetrade.backend.security.JwtResolver;
import com.eyetrade.backend.service.AnnotationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 8 Ara 2019
 */

@Api(value = "Annotation", tags = {"Annotations of Article or Events"})
@RestController
@RequestMapping("/annotation")
public class AnnotationController {

    @Autowired
    private AnnotationService service;

    @Autowired
    private JwtResolver jwtResolver;

    @ApiOperation(value = "Add annotation to an article or event", response = AnnotationResource.class)
    @PostMapping("/add")
    public ResponseEntity<AnnotationResource> addAnnotation(@RequestHeader("Authorization") String token, @RequestBody AnnotationDto dto) {
        return ResponseEntity.ok(service.addAnnotation(dto, jwtResolver.getIdFromToken(token)));
    }
    @ApiOperation(value = "Get annotation with annotation id", response = AnnotationResource.class)
    @GetMapping("/get")
    public ResponseEntity<AnnotationResource> getAnnotation(@RequestParam UUID annotationId) {
        return ResponseEntity.ok(service.getAnnotation(annotationId));
    }
    // TODO: 8 Ara 2019 delete ekle.

    @ApiOperation(value = "Get annotations of an article or an event with article event id and token", response = AnnotationResource.class,responseContainer = "List")
    @GetMapping("/get_annotations_of_article_event")
    public ResponseEntity<List<AnnotationResource>> getAnnotationsOfArticleEvent(@RequestParam UUID articleEventId) {
        return ResponseEntity.ok(service.getAnnotationsOfArticleEvent(articleEventId));
    }

    @ApiOperation(value = "Get annotations of self user with token", response = AnnotationResource.class,responseContainer = "List")
    @GetMapping("/get_annotations_of_self")
    public ResponseEntity<List<AnnotationResource>> getAnnotationsOfSelf(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.getAnnotationsOfSelf(jwtResolver.getIdFromToken(token)));
    }

    @ApiOperation(value = "Delete annotation of user with annotation id and token", response = String.class)
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAnnotation(@RequestHeader("Authorization") String token,@RequestParam UUID annotationId) throws IllegalAccessException {
        return ResponseEntity.ok(service.deleteAnnotation(annotationId,jwtResolver.getIdFromToken(token)));
    }
}
