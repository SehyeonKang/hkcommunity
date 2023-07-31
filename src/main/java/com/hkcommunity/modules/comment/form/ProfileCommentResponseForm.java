package com.hkcommunity.modules.comment.form;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfileCommentResponseForm {

    private Long id;
    private Long postId;
    private String content;
    private String boardCategory;
    private LocalDateTime createdDateTime;

    @QueryProjection
    public ProfileCommentResponseForm(Long id, Long postId, String content, String boardCategory, LocalDateTime createdDateTime) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.boardCategory = boardCategory;
        this.createdDateTime = createdDateTime;
    }
}
