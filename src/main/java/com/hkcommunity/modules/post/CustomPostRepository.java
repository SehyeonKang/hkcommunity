package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.post.form.BoardResponseForm;
import com.hkcommunity.modules.post.form.ProfilePostResponseForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {

    Page<BoardResponseForm> selectPostList(String boardCategory, String category, Pageable pageable);
    Page<BoardResponseForm> selectPostListWithKeyword(String boardCategory, String category, String searchType, String searchKeyword, Pageable pageable);

    Page<ProfilePostResponseForm> selectProfilePostList(String author, Pageable pageable);

    void addAuthor(Account account, Post post);

    void plusViewCount(Post post);

    void minusViewCount(Post post);

    void plusLikeCount(Post post);

    void minusLikeCount(Post post);

    void plusCommentCount(Post post);

    void minusCommentCount(Post post);
}
