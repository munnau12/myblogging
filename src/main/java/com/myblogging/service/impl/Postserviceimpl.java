package com.myblogging.service.impl;

import com.myblogging.entity.Post;
import com.myblogging.exception.ResourceNotFoundException;
import com.myblogging.payload.PostDto;
import com.myblogging.payload.PostResponse;
import com.myblogging.repository.PostRepository;
import com.myblogging.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Postserviceimpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public Postserviceimpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post= mapToEntity(postDto);
         Post newpost = postRepository.save(post);
         PostDto newpostdto = mapToDto(newpost);
        return newpostdto;
    }
    @Override
    public PostResponse getAllposts(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
              Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> content = posts.getContent();
        List<PostDto> postDtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
       PostResponse postResponse=new PostResponse();
       postResponse.setContent(postDtos);
       postResponse.setPageNo(posts.getNumber());
       postResponse.setPageSize(posts.getSize());
       postResponse.setTotalElements(posts.getTotalElements());
       postResponse.setTotalPage(posts.getTotalPages());
       postResponse.setLast(posts.isLast());
       return postResponse;
    }
    @Override
    public PostDto GetPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDto(post);
    }
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
         Post save = postRepository.save(post);
        return mapToDto(save);
    }

    @Override
    public void deletePostById(long id) {
        postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id));
        postRepository.deleteById(id);
    }
    Post mapToEntity(PostDto postDto){
        Post post=modelMapper.map(postDto,Post.class);
       // Post post=new Post();
       // post.setTitle(postDto.getTitle());
       // post.setDescription(postDto.getDescription());
       // post.setContent(postDto.getContent());
        return post;
     }
      PostDto mapToDto(Post post){
        PostDto postDto=modelMapper.map(post,PostDto.class);
       // PostDto postDto=new PostDto();
       // postDto.setId(post.getId());
       // postDto.setTitle(post.getTitle());
       // postDto.setDescription(post.getDescription());
       // postDto.setContent(post.getContent());
        return postDto;
      }
}
