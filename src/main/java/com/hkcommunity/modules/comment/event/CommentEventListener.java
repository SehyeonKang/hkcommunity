package com.hkcommunity.modules.comment.event;

import com.hkcommunity.modules.account.form.AccountDto;
import com.hkcommunity.modules.notification.NotificationService;
import com.hkcommunity.modules.notification.form.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    @Async
    public void handleNotification(CommentCreatedEvent event) {
        log.info("CommentEventListener.handleNotification");

        if (isAbleToSendToPostWriter(event)) {
            noticeToPostWriter(event.getPublisher(), event.getPostWriter(), event.getPostId(), event.getPostBoardCategory(), event.getPostTitle(), event.getContent(), event.getCreatedDateTime());
        }

        if (isAbleToSendToParentWriter(event)) {
            noticeToParentWriter(event.getPublisher(), event.getParentWriter(), event.getPostId(), event.getPostBoardCategory(), event.getPostTitle(), event.getContent(), event.getCreatedDateTime());
        }

        throw new RuntimeException();
    }

    private void noticeToPostWriter(AccountDto publisherDto, AccountDto postWriterDto, Long postId, String postBoardCategory, String postTitle, String content, LocalDateTime createdDateTime) {
        NotificationDto notificationDto = new NotificationDto(publisherDto, postWriterDto, postId, postBoardCategory, postTitle, content, createdDateTime);
        notificationService.saveNotificationToPostWriter(publisherDto, postWriterDto, notificationDto);
    }

    private void noticeToParentWriter(AccountDto publisherDto, AccountDto parentWriterDto, Long postId, String postBoardCategory, String postTitle, String content, LocalDateTime createdDateTime) {
        NotificationDto notificationDto = new NotificationDto(publisherDto, parentWriterDto, postId, postBoardCategory, postTitle, content, createdDateTime);
        notificationService.saveNotificationToParentWriter(publisherDto, parentWriterDto, notificationDto);
    }

    private boolean isAbleToSendToPostWriter(CommentCreatedEvent event) {
        if (!isSameMember(event.getPublisher(), event.getPostWriter())) {
            if (hasParent(event))
                return !isSameMember(event.getPostWriter(), event.getParentWriter());

            return true;
        }
        return false;
    }

    private boolean isAbleToSendToParentWriter(CommentCreatedEvent event) {
        return hasParent(event) && !isSameMember(event.getPublisher(), event.getParentWriter());
    }

    private boolean isSameMember(AccountDto a, AccountDto b) {
        return Objects.equals(a.getId(), b.getId());
    }

    private boolean hasParent(CommentCreatedEvent event) {
        return event.getParentWriter().getId() != null;
    }
}
