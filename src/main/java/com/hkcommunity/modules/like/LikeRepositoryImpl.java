package com.hkcommunity.modules.like;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.hkcommunity.modules.like.QLike.like;

public class LikeRepositoryImpl implements CustomLikeRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public LikeRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Like> existsLikeByAccountAndPost(Long accountId, Long postId) {
        Like postLike = jpaQueryFactory
                .selectFrom(like)
                .where(like.account.id.eq(accountId),
                        like.post.id.eq(postId))
                .fetchFirst();

        return Optional.ofNullable(postLike);
    }

    public Long findLikeNum(Long postId) {
        Long count = jpaQueryFactory
                .select(like.count())
                .from(like)
                .where(like.post.id.eq(postId))
                .fetchOne();

        return count;
    }
}
