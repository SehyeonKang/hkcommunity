package com.hkcommunity.modules.comment;

import com.hkcommunity.modules.comment.form.ProfileCommentResponseForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCommentRepository {

    Page<ProfileCommentResponseForm> selectProfileCommentList(String author, Pageable pageable);
}
