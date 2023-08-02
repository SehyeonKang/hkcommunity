package com.hkcommunity.modules.comment.event;

import com.hkcommunity.modules.account.form.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentCreatedEvent {

    private AccountDto publisher;
    private AccountDto postWriter;
    private AccountDto parentWriter;
    private Long postId;
    private String postTitle;
    private String content;
    private LocalDateTime createdDateTime;
}
