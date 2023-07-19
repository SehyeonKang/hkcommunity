package com.hkcommunity.modules.post.form;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter @Setter
@Builder
public class PostResponseForm {

    private String author;

    private String title;

    private String content;

    private LocalDateTime publishedDateTime;

    private LocalDateTime modifiedDateTime;

}
