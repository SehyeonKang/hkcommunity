package com.hkcommunity.modules.comment;

import com.hkcommunity.modules.comment.form.ProfileCommentResponseForm;
import com.hkcommunity.modules.comment.form.QProfileCommentResponseForm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hkcommunity.modules.comment.QComment.comment;
import static com.hkcommunity.modules.post.QPost.post;

public class CommentRepositoryImpl implements CustomCommentRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ProfileCommentResponseForm> selectProfileCommentList(String author, Pageable pageable) {
        List<ProfileCommentResponseForm> content = getProfileCommentList(author, pageable);
        Long count = getProfileCommentCount(author);
        return new PageImpl<>(content, pageable, count);
    }

    private Long getProfileCommentCount(String author) {
        Long count = jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.account.nickname.eq(author))
                .fetchOne();

        return count;
    }

    private List<ProfileCommentResponseForm> getProfileCommentList(String author, Pageable pageable) {
        List<ProfileCommentResponseForm> content = jpaQueryFactory
                .select(new QProfileCommentResponseForm(
                        comment.id,
                        post.id,
                        comment.content,
                        post.boardCategory,
                        comment.createdDateTime))
                .from(comment)
                .leftJoin(comment.post, post)
                .where(comment.account.nickname.eq(author),
                        comment.deleted.eq(false))
                .orderBy(comment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }
}
