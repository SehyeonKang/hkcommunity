package com.hkcommunity.factory;

import com.hkcommunity.modules.comment.form.CommentReadCondition;

public class CommentReadConditionFactory {

    public static CommentReadCondition createCommentReadCondition() {
        return new CommentReadCondition(1L);
    }

    public static CommentReadCondition createCommentReadCondition(Long postId) {
        return new CommentReadCondition(postId);
    }
}
