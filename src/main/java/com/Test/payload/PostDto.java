package com.Test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private long id;

    @Size(min=2,message = "Title can't be less than 2")
    private String title;

    @Size(min=4,message = "Description can't be less than 4")
    private String description;

    @Size(min=4,message = "Content can't be less than 4")
    private String content;

    private String message;
}
