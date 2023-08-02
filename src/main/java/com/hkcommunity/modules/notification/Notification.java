package com.hkcommunity.modules.notification;

import com.hkcommunity.modules.account.Account;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private String postTitle;

    private String content;

    private String publisher;

    private String publisherProfileImage;

    private boolean checked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private LocalDateTime createdDateTime;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Builder
    public Notification(Long postId, String postTitle, String content, String publisher, String publisherProfileImage, Account account, LocalDateTime createdDateTime, NotificationType notificationType) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.content = content;
        this.publisher = publisher;
        this.publisherProfileImage = publisherProfileImage;
        this.checked = false;
        this.account = account;
        this.createdDateTime = createdDateTime;
        this.notificationType = notificationType;
    }
}
