package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.CommentDto;
import com.eyetrade.backend.model.resource.comment.CommentResource;
import com.eyetrade.backend.security.JwtResolver;
import com.eyetrade.backend.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */

@Api(value = "Comments", tags = {"Operations Related with Comments of Article or Events"})
@RestController
@RequestMapping("/comment_controller")
public class CommentController {

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private CommentService service;

    @ApiOperation(value = "A user can comment on event or article with necessary informations.", response = CommentResource.class)
    @PostMapping("/post-comment")
    public ResponseEntity<CommentResource> postComment(@RequestHeader("Authorization") String token,
                                                       @RequestBody CommentDto dto){
        UUID userId=jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.postComment(dto,userId));
    }

    @ApiOperation(value = "A user can access a comment on event or article with Comment ID.", response = CommentResource.class)
    @GetMapping("/get-comment")
    public ResponseEntity<CommentResource> getComment(@RequestHeader("Authorization") String token,
                                                       @RequestParam UUID commentId){
        jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.getComment(commentId));
    }

    @ApiOperation(value = "A user can access all comments about an event or an article with Event or Article Id.", response = CommentResource.class)
    @GetMapping("/get-comments-of-article")
    public ResponseEntity<List<CommentResource>> getCommentsOfArticleOrEvent(@RequestHeader("Authorization") String token,
                                                                            @RequestParam UUID articleOrEventId){
        jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.getCommentsOfArticleOrEvent(articleOrEventId));
    }


}