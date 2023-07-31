package com.hkcommunity.modules.post;

import com.hkcommunity.modules.post.form.BoardResponseForm;
import com.hkcommunity.modules.post.form.ProfilePostResponseForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {
    Page<BoardResponseForm> selectPostList(String boardCategory, String searchKeyword, Pageable pageable);

    Page<ProfilePostResponseForm> selectProfilePostList(String author, Pageable pageable);

    void plusCommentCount(Post post);

    void minusCommentCount(Post post);
}
