package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.UserAccount;
import com.hkcommunity.modules.comment.Comment;
import com.hkcommunity.modules.like.Like;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //즉시로딩(EAGER)은 예측이 어렵고, 어떤 SQL이 실행되는지 추적하기가 어려움
    @ManyToOne(fetch = FetchType.LAZY)
    private Account author;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();

    private String title;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String content;

    private String boardCategory;

    private String postCategory;

    private Long viewCount;

    private Long likeCount;

    private Long commentCount;

    @CreatedDate
    private LocalDateTime publishedDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    public Post(String title, String content, Account author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = 0L;
        this.likeCount = 0L;
    }

    public void addAuthor(Account account) {
        this.publishedDateTime = LocalDateTime.now();
        this.author = account;
        this.viewCount = 0L;
        this.likeCount = 0L;
        this.commentCount = 0L;
    }

    public boolean isAuthor(UserAccount userAccount) {
        return this.author.equals(userAccount.getAccount());
    }

    public Post plusViewCount() {
        this.viewCount++;
        return this;
    }

    public Post minusViewCount() {
        this.viewCount--;
        return this;
    }

    public Post plusLikeCount() {
        this.likeCount++;
        return this;
    }

    public Post minusLikeCount() {
        this.likeCount--;
        return this;
    }

    public Post plusCommentCount() {
        this.commentCount++;
        return this;
    }

    public Post minusCommentCount() {
        this.commentCount--;
        return this;
    }
}
