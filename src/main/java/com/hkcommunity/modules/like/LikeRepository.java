package com.hkcommunity.modules.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<Like, Long>, CustomLikeRepository {
}
