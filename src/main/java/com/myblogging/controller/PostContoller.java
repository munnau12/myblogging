package com.myblogging.controller;

import com.myblogging.payload.PostDto;
import com.myblogging.payload.PostResponse;
import com.myblogging.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostContoller {

    private PostService postService;

    public PostContoller(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
   public ResponseEntity<Object>createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
   }
   @GetMapping
     public PostResponse getAllposts(
             @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
             @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
             @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
             @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir){
       return postService.getAllposts(pageNo,pageSize,sortBy,sortDir);
     }

     @GetMapping("/{id}")
     public ResponseEntity<PostDto> getPostById(@PathVariable("id")long id){
        PostDto post = postService.GetPostById(id);
        return ResponseEntity.ok(post);
     }
    @PreAuthorize("hasRole('ADMIN')")
     @PutMapping("/{id}")
     public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("id")long id){
        PostDto postDto1 = postService.updatePost(postDto, id);
         return new ResponseEntity<>(postDto1,HttpStatus.OK);
     }
    @PreAuthorize("hasRole('ADMIN')")
     @DeleteMapping("/{id}")
     public ResponseEntity<String> deletePostById(@PathVariable("id")long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("post deleted!! ",HttpStatus.OK);
     }
}