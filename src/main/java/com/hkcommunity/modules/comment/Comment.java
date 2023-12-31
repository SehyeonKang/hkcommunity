package com.hkcommunity.modules.comment;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.form.AccountDto;
import com.hkcommunity.modules.comment.event.CommentCreatedEvent;
import com.hkcommunity.modules.comment.form.CommentUpdateRequest;
import com.hkcommunity.modules.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Table(name = "comments")
@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private boolean deleted;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public Comment(String content, Account account, Post post, Comment parent) {
        this.content = content;
        this.account = account;
        this.post = post;
        this.parent = parent;
        this.deleted = false;
    }

    public Optional<Comment> findDeletableComment() {
        return hasChildren() ? Optional.empty() : Optional.of(findDeletableCommentByParent());
    }

    public void update(CommentUpdateRequest request) {
        this.content = request.getContent();
    }

    public void delete() {
        this.deleted = true;
    }

    private Comment findDeletableCommentByParent() {
        if (isDeletableParent()) {
            Comment deletableParent = getParent().findDeletableCommentByParent();
            if (getParent().getChildren().size() == 1) {
                return deletableParent;
            }
        }

        return this;
    }

    private boolean hasChildren() {
        return getChildren().size() != 0;
    }

    private boolean isDeletableParent() {
        return getParent() != null && getParent().isDeleted();
    }

    public void publishCreatedEvent(ApplicationEventPublisher eventPublisher) {
        eventPublisher.publishEvent(
                new CommentCreatedEvent(
                        AccountDto.toDto(getAccount()),
                        AccountDto.toDto(getPost().getAuthor()),
                        Optional.ofNullable(getParent()).map(p -> p.getAccount()).map(a -> AccountDto.toDto(a)).orElseGet(() -> AccountDto.empty()),
                        getPost().getId(),
                        getPost().getBoardCategory(),
                        getPost().getTitle(),
                        getContent(),
                        getCreatedDateTime()
                )
        );
    }
}
