package com.myblogging.service;

import com.myblogging.payload.PostDto;
import com.myblogging.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);


    PostResponse getAllposts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto GetPostById(long id);


    PostDto updatePost(PostDto postDto, long id);


    void deletePostById(long id);
}
