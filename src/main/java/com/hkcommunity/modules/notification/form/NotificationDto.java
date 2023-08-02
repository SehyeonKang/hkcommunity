package com.hkcommunity.modules.notification.form;

import com.hkcommunity.modules.account.form.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationDto {

    private AccountDto publisher;
    private AccountDto target;
    private Long postId;
    private String postTitle;
    private String content;
    private LocalDateTime createdDateTime;
}
