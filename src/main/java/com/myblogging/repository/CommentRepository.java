package com.myblogging.repository;

import com.myblogging.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
     List<Comment>findAllBypostId(long postId);
}
