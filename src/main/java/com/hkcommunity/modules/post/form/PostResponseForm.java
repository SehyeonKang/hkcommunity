package com.hkcommunity.modules.post.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponseForm {

    private Long postNum;
    private Long viewCount;
    private String author;
    private String title;
    private String content;
    private String publishedDateTime;
    private boolean authorChecked;

}
