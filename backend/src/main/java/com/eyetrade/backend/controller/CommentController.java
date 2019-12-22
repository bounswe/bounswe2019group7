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

@Api(value = "Comments", tags = {"Comments of Article or Events"})
@RestController
@RequestMapping("/comment_controller")
public class CommentController {

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private CommentService service;

    @ApiOperation(value = "A user can comment on event or article with necessary informations.", response = CommentResource.class)
    @PostMapping("/post_comment")
    public ResponseEntity<CommentResource> postComment(@RequestHeader("Authorization") String token,
                                                       @RequestBody CommentDto dto){
        UUID userId=jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.postComment(dto,userId));
    }

    @ApiOperation(value = "A user can access a comment on event or article with Comment ID.", response = CommentResource.class)
    @GetMapping("/get_comment")
    public ResponseEntity<CommentResource> getComment(@RequestHeader("Authorization") String token,
                                                       @RequestParam UUID commentId){
        jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.getComment(commentId));
    }

    @ApiOperation(value = "A user can access all comments about an event or an article with Event or Article Id.", response = CommentResource.class, responseContainer = "List")
    @GetMapping("/get_comments_of_article")
    public ResponseEntity<List<CommentResource>> getCommentsOfArticleOrEvent(@RequestHeader("Authorization") String token,
                                                                            @RequestParam UUID articleOrEventId){
        jwtResolver.getIdFromToken(token);
        return ResponseEntity.ok(service.getCommentsOfArticleOrEvent(articleOrEventId));
    }

    @ApiOperation(value = "A user can access all comments about of himself/herself.", response = CommentResource.class ,responseContainer = "List")
    @GetMapping("/get_comments_of_user")
    public ResponseEntity<List<CommentResource>> getCommentsOfUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(service.getCommentsOfUser(jwtResolver.getIdFromToken(token)));
    }

    @ApiOperation(value = "A user can delete his/her your own comment with comment id and token.", response = String.class)
    @DeleteMapping("/delete_comment")
    public ResponseEntity deleteComment(@RequestHeader("Authorization") String token, @RequestParam UUID articleOrEventId)  {
        try {
            return ResponseEntity.ok(service.deleteComment(articleOrEventId,jwtResolver.getIdFromToken(token)));
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "A user can change content of his/her your own comment with commentDto, comment id and token.", response = CommentResource.class)
    @PutMapping("/update_comment")
    public ResponseEntity updateComment(@RequestHeader("Authorization") String token, @RequestParam String newContent, @RequestParam UUID articleOrEventId)  {
        try {
            return ResponseEntity.ok(service.updateComment(newContent,articleOrEventId,jwtResolver.getIdFromToken(token)));
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
