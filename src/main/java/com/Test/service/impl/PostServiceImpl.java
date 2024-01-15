package com.Test.service.impl;

import com.Test.entity.Post;
import com.Test.exception.ResourceNotFound;
import com.Test.payload.PostDto;
import com.Test.repository.PostRepository;
import com.Test.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private PostDto mapToDto(Post post) {

        PostDto dto=new PostDto();

        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getDescription());

        return dto;
    }
    @Override
    public PostDto createPost(PostDto postDto) {

        Post post=new Post();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post saved = postRepository.save(post);

       PostDto dto= mapToDto(saved);

        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post is not found with id "+id)
        );
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortdir) {

        Sort sort = sortdir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable=PageRequest.of(pageNo,pageSize,sort);

        Page<Post> all = postRepository.findAll(pageable);

        List<PostDto> collect = all.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        return collect;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post is not found with id: " + id)
        );

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(post.getDescription());

        Post saved = postRepository.save(post);

        PostDto dto = mapToDto(saved);


        return dto;
    }


}
