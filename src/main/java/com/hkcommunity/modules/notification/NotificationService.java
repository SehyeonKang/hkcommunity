package com.hkcommunity.modules.notification;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.AccountRepository;
import com.hkcommunity.modules.account.form.AccountDto;
import com.hkcommunity.modules.notification.form.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.hkcommunity.modules.notification.NotificationType.COMMENT;
import static com.hkcommunity.modules.notification.NotificationType.REPLY;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    public void saveNotificationToPostWriter(AccountDto publisherDto, AccountDto postWriterDto, NotificationDto notificationDto) {
        log.info("{}님이 {}에 댓글을 달았습니다. 내용: {}, 작성일: {}",
                notificationDto.getPublisher().getNickname(), notificationDto.getPostTitle(), notificationDto.getContent(), notificationDto.getCreatedDateTime());

        String publisher = publisherDto.getNickname();
        String publisherProfileImage = publisherDto.getProfileImage();
        Account account = accountRepository.findById(postWriterDto.getId()).get();
        String convertedCreatedDateTime = notificationDto.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Notification notification = Notification.builder()
                .postLink("/" + notificationDto.getPostBoardCategory() + "/" + notificationDto.getPostId())
                .postTitle(notificationDto.getPostTitle())
                .content(notificationDto.getContent())
                .publisher(publisher)
                .publisherProfileImage(publisherProfileImage)
                .account(account)
                .createdDateTime(convertedCreatedDateTime)
                .notificationType(COMMENT)
                .build();

        notificationRepository.save(notification);
    }

    public void saveNotificationToParentWriter(AccountDto publisherDto, AccountDto parentWriterDto, NotificationDto notificationDto) {
        log.info("{}님이 {}에서 대댓글을 달았습니다. 내용: {}, 작성일: {}",
                notificationDto.getTarget().getNickname(), notificationDto.getPostTitle(), notificationDto.getContent(), notificationDto.getCreatedDateTime());

        String publisher = publisherDto.getNickname();
        String publisherProfileImage = publisherDto.getProfileImage();
        Account account = accountRepository.findById(parentWriterDto.getId()).get();
        String convertedCreatedDateTime = notificationDto.getCreatedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Notification notification = Notification.builder()
                .postLink("/" + notificationDto.getPostBoardCategory() + "/" + notificationDto.getPostId())
                .postTitle(notificationDto.getPostTitle())
                .content(notificationDto.getContent())
                .publisher(publisher)
                .publisherProfileImage(publisherProfileImage)
                .account(account)
                .createdDateTime(convertedCreatedDateTime)
                .notificationType(REPLY)
                .build();

        notificationRepository.save(notification);
    }

    public void changeReadCondition(List<Notification> notifications) {
        notifications.forEach(n -> notificationRepository.changeReadCondition(n, true));
        notificationRepository.saveAll(notifications);
    }
}
