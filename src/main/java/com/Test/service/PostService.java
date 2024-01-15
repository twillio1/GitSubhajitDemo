package com.Test.service;

import com.Test.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    void deletePost(long id);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortdir);

    PostDto updatePost(PostDto postDto, long id);
}
