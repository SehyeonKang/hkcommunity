package com.hkcommunity.modules.post.form;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardResponseForm {

    private Long id;
    private Long viewCount;
    private Long likeCount;
    private String title;

    private String postCategory;
    private String author;
    private LocalDateTime publishedDateTime;

    @QueryProjection
    public BoardResponseForm(Long id, Long viewCount, Long likeCount, String title, String postCategory, String author, LocalDateTime publishedDateTime) {
        this.id = id;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.title = title;
        this.postCategory = postCategory;
        this.author = author;
        this.publishedDateTime = publishedDateTime;
    }
}
