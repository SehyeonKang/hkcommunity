package com.hkcommunity.modules.comment.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hkcommunity.modules.account.form.AccountDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentUpdateResponse {

    private Long id;
    private String content;
    private AccountDto account;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDateTime;
}
