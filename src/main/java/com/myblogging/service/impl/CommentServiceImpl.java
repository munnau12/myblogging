package com.myblogging.service.impl;

import com.myblogging.entity.Comment;
import com.myblogging.entity.Post;
import com.myblogging.exception.BlogApiEException;
import com.myblogging.exception.ResourceNotFoundException;
import com.myblogging.payload.CommentDTO;
import com.myblogging.repository.CommentRepository;
import com.myblogging.repository.PostRepository;
import com.myblogging.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private ModelMapper modelMapper;
    public CommentServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
       Comment comment= mapToEntity(commentDTO);
         Post post = postRepository.findById(postId).orElseThrow(()
                 -> new ResourceNotFoundException("post", "id", postId));
         comment.setPost(post);
         Comment newcomment = commentRepository.save(comment);
        CommentDTO commentDTO1= mapToDto(newcomment);
        return commentDTO1;
    }

    @Override
    public List<CommentDTO> getCommentBypostId(long postId) {
        List<Comment> comments = commentRepository.findAllBypostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
         Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiEException(HttpStatus.BAD_REQUEST,"comment does not belongs to this post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDTO UpdateComment(long postId, long id, CommentDTO commentDTO) {
         Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));
         Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", id));
         if(comment.getPost().getId()!=post.getId()){
             throw new BlogApiEException(HttpStatus.BAD_REQUEST,"post not match");
         }
         comment.setId(id);
         comment.setName(commentDTO.getName());
         comment.setEmail(commentDTO.getEmail());
         comment.setBody(commentDTO.getBody());
         Comment newcomment = commentRepository.save(comment);
        return mapToDto(newcomment);
    }

    @Override
    public void DeleteComment(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", id));
        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiEException(HttpStatus.BAD_REQUEST,"post not match");
        }
        commentRepository.deleteById(id);
    }

    private CommentDTO mapToDto(Comment newcomment) {
        CommentDTO commentDTO=modelMapper.map(newcomment,CommentDTO.class);
        //CommentDTO commentDTO=new CommentDTO();
        //commentDTO.setId(newcomment.getId());
        //commentDTO.setName(newcomment.getName());
        //commentDTO.setEmail(newcomment.getEmail());
        //commentDTO.setBody(newcomment.getBody());
        //commentDTO.setPost(newcomment.getPost());
        return commentDTO ;
    }
    private Comment mapToEntity(CommentDTO commentDTO) {
        Comment comment=modelMapper.map(commentDTO,Comment.class);
//        Comment comment=new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return comment;
    }
}
