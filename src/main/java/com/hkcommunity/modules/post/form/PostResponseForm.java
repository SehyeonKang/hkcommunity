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

    private Long postNum;

    private String author;

    private String title;

    private String content;

    private String publishedDateTime;

    private boolean authorChecked;

    private LocalDateTime modifiedDateTime;

}
