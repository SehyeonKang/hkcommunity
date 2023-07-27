package com.hkcommunity.modules.comment.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CommentUpdateRequest {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;
}
