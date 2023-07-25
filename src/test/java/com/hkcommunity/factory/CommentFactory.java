package com.hkcommunity.factory;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.comment.Comment;
import com.hkcommunity.modules.post.Post;

import static com.hkcommunity.factory.AccountFactory.createAccount;
import static com.hkcommunity.factory.PostFactory.createPost;

public class CommentFactory {

    public static Comment createComment(Comment parent) {
        return new Comment("content", createAccount(), createPost(), parent);
    }

    public static Comment createDeletedComment(Comment parent) {
        Comment comment = new Comment("content", createAccount(), createPost(), parent);
        comment.delete();
        return comment;
    }

    public static Comment createComment(Account author, Post post, Comment parent) {
        return new Comment("content", author, post, parent);
    }
}
