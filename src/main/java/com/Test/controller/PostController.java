package com.Test.controller;

import com.Test.payload.PostDto;
import com.Test.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
           return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto=postService.createPost(postDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id)
    {
       postService.deletePost(id);
       return new ResponseEntity<>("Post is deleted with id :"+id,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
         @RequestParam(name="pageNo", defaultValue = "0",required = false) int pageNo,
         @RequestParam(name="pageSize", defaultValue = "2",required = false) int pageSize,
         @RequestParam(name="sortBy", defaultValue = "id",required = false) String sortBy,
         @RequestParam(name="sortDir", defaultValue = "asc",required = false) String sortdir
    ){
       List<PostDto> dtos=postService.getAllPosts(pageNo,pageSize,sortBy,sortdir);
       return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable long id)
    {
        PostDto dto=postService.updatePost(postDto,id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
