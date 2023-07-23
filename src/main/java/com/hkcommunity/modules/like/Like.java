package com.hkcommunity.modules.like;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.post.Post;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter @EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor
public class Like {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public Like(Account account, Post post) {
        this.account = account;
        this.post = post;
    }
}
