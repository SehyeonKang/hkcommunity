package com.hkcommunity.modules.post.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PostForm {

    @NotBlank
    @Length(max = 100)
    private String title;

    @NotBlank
    private String content;
}
