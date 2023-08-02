package com.hkcommunity.modules.post.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponseForm {

    private Long id;
    private Long viewCount;
    private String author;
    private String authorProfileImage;
    private String title;
    private String content;
    private String postCategory;
    private String publishedDateTime;
    private boolean authorChecked;

}
