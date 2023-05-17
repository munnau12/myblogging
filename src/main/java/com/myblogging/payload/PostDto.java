package com.myblogging.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;
    @NotEmpty(message = "2 char is mandatory")
    @Size(min = 2,message = "atleast have 2 char")
    private String title;
    @NotEmpty
    @Size(min = 4,message = "atleast have 4 char")
    private String description;
    @NotEmpty
    @Size(min = 10,message = "atleast have 10 char")
    private String content;
}
