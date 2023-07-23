package com.hkcommunity.modules.like;

import java.util.Optional;

public interface CustomLikeRepository {
    Optional<Like> existsLikeByAccountAndPost(Long accountId, Long postId);

    public Long findLikeNum(Long postId);
}
