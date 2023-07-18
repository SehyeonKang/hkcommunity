package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Post {

    @Id @GeneratedValue
    private Long id;

    //즉시로딩(EAGER)은 예측이 어렵고, 어떤 SQL이 실행되는지 추적하기가 어려움
    @ManyToOne(fetch = FetchType.LAZY)
    private Account author;

    private String title;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String content;

    private LocalDateTime publishedDateTime;

    private LocalDateTime modifiedDateTime;

    public void addAuthor(Account account) {
        this.author = account;
    }
}
