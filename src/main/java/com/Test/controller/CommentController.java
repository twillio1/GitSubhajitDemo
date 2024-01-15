package com.Test.controller;

import com.Test.payload.CommentDto;
import com.Test.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable long id)
    {
        CommentDto dto=commentService.createComment(id,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments()
    {
        List<CommentDto> dtos=commentService.getAllComments();
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId)
    {
        List<CommentDto> dtos=commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }
}
