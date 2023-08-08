package com.hkcommunity.modules.notification;

import com.hkcommunity.modules.account.Account;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postLink;

    private String postTitle;

    private String content;

    private String publisher;

    @Lob
    private String publisherProfileImage;

    private boolean checked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private String createdDateTime;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Builder
    public Notification(String postLink, String postTitle, String content, String publisher, String publisherProfileImage, Account account, String createdDateTime, NotificationType notificationType) {
        this.postLink = postLink;
        this.postTitle = postTitle;
        this.content = content;
        this.publisher = publisher;
        this.publisherProfileImage = publisherProfileImage;
        this.checked = false;
        this.account = account;
        this.createdDateTime = createdDateTime;
        this.notificationType = notificationType;
    }

    public void changeReadCondition(boolean checked) {
        this.checked = checked;
    }
}
