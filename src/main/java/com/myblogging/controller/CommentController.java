package com.myblogging.controller;

import com.myblogging.payload.CommentDTO;
import com.myblogging.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/1/comments
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Object> createComment( @PathVariable("postId")long postId,
                                                   @Valid@RequestBody CommentDTO commentDTO,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
         CommentDTO dto = commentService.createComment(postId, commentDTO);
         return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentBypostId(@PathVariable("postId")long postId){
       return commentService.getCommentBypostId(postId);
    }

    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable("postId")long postId,
                                                     @PathVariable("id")long commentId){
         CommentDTO dto = commentService.getCommentById(postId, commentId);
         return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> UpdateComment(@PathVariable("postId")long postId,
                                                    @PathVariable("id")long id,
                                                    @RequestBody CommentDTO commentDTO){
       CommentDTO dto= commentService.UpdateComment(postId,id,commentDTO);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> DeleteComment(@PathVariable("postId")long postId,
                                                @PathVariable("id")long id){
        commentService.DeleteComment(postId,id);
        return new  ResponseEntity<>("comment is deleted sucessfully",HttpStatus.OK);
    }
}
