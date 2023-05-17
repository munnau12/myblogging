package com.myblogging.payload;

import com.myblogging.entity.Post;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private long id;
    @NotEmpty
    @Size(min = 2,message = "atleast have 2 char")
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 3,message = "atleast have 3 char")
    private String body;
}
