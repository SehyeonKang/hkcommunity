package com.hkcommunity.modules.post;

import com.hkcommunity.modules.post.form.BoardResponseForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {
    Page<BoardResponseForm> selectPostList(String searchKeyword, Pageable pageable);
}
