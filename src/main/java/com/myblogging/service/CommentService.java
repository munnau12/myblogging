package com.myblogging.service;

import com.myblogging.entity.Comment;
import com.myblogging.payload.CommentDTO;

import java.util.List;


public interface CommentService {

    CommentDTO createComment(long postId, CommentDTO commentDTO);

    List<CommentDTO> getCommentBypostId(long postId);

    CommentDTO getCommentById(long postId,long commentId);

    CommentDTO UpdateComment(long postId, long id, CommentDTO commentDTO);

    void DeleteComment(long postId, long id);
}
