package com.Test.service.impl;

import com.Test.entity.Comment;
import com.Test.entity.Post;
import com.Test.exception.ResourceNotFound;
import com.Test.payload.CommentDto;
import com.Test.repository.CommentRepository;
import com.Test.repository.PostRepository;
import com.Test.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    CommentDto mapToDto(Comment comment)
    {
        CommentDto dto=new CommentDto();

        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());

        return dto;
    }
    @Override
    public CommentDto createComment(long id, CommentDto commentDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post is not found with id " + id)
        );

        Comment comment=new Comment();

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        comment.setPost(post);

        Comment saved = commentRepository.save(comment);

        CommentDto dto = mapToDto(saved);
        return  dto;

    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> all = commentRepository.findAll();

        List<CommentDto> collect = all.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post is not found with id " + postId)
        );
        List<Comment> all = commentRepository.findAllByPostId(postId);
        List<CommentDto> collect = all.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return collect;
    }
}
