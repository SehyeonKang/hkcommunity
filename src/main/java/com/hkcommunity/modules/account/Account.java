package com.hkcommunity.modules.account;

import com.hkcommunity.modules.comment.Comment;
import com.hkcommunity.modules.post.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String userId;

    private String password;
    private String emailCheckToken;

    private String introduction;

    private boolean emailVerified;

    private LocalDateTime joinedAt;

    private LocalDateTime emailCheckTokenGeneratedAt;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    @Builder.Default
    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "account")
    private List<Comment> comments = new ArrayList<>();

    public Account(String email, String nickname, String userId, String password) {
        this.email = email;
        this.nickname = nickname;
        this.userId = userId;
        this.password = password;
        this.emailCheckToken = UUID.randomUUID().toString();
        this.introduction = null;
        this.emailVerified = false;
        this.joinedAt = LocalDateTime.now();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
        this.profileImage = null;
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusSeconds(5));
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public boolean isAuthor(Post post) {
        return post.getAuthor().equals(this);
    }

    public boolean isPublisher(Comment comment) {
        return comment.getAccount().equals(this);
    }
}
