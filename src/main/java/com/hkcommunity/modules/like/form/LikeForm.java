package com.hkcommunity.modules.like.form;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.post.Post;
import lombok.Data;

@Data
public class LikeForm {

    private Account account;
    private Post post;

    public LikeForm(Account account, Post post) {
        this.account = account;
        this.post = post;
    }
}
