package com.Test.service;

import com.Test.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long id, CommentDto commentDto);

    List<CommentDto> getAllComments();

    List<CommentDto> getAllCommentsByPostId(long postId);
}
