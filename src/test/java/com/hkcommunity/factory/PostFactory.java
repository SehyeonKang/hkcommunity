package com.hkcommunity.factory;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.post.Post;

import static com.hkcommunity.factory.AccountFactory.createAccount;

public class PostFactory {

    public static Post createPost() {
        return createPost(createAccount());
    }

    public static Post createPost(Account author) {
        return new Post("title", "content", author);
    }
}
