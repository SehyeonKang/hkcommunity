package com.hkcommunity.modules.post.form;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfilePostResponseForm {

    private Long id;
    private Long viewCount;
    private String title;
    private String boardCategory;
    private LocalDateTime publishedDateTime;

    @QueryProjection
    public ProfilePostResponseForm(Long id, Long viewCount, String title, String boardCategory, LocalDateTime publishedDateTime) {
        this.id = id;
        this.viewCount = viewCount;
        this.title = title;
        this.boardCategory = boardCategory;
        this.publishedDateTime = publishedDateTime;
    }
}
