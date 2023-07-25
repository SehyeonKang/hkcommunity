package com.hkcommunity.modules.post;

import com.hkcommunity.modules.post.form.BoardResponseForm;
import com.hkcommunity.modules.post.form.QBoardResponseForm;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hkcommunity.modules.account.QAccount.account;
import static com.hkcommunity.modules.post.QPost.post;

public class PostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<BoardResponseForm> selectPostList(String searchKeyword, Pageable pageable) {
        List<BoardResponseForm> content = getPostList(searchKeyword, pageable);
        Long count = getCount(searchKeyword);
        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(String searchKeyword) {
        Long count = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(containsSearchKeyword(searchKeyword))
                .fetchOne();

        return count;
    }

    private List<BoardResponseForm> getPostList(String searchKeyword, Pageable pageable) {
        List<BoardResponseForm> content = jpaQueryFactory
                .select(new QBoardResponseForm(
                        post.id,
                        post.viewCount,
                        post.likeCount,
                        post.title,
                        account.nickname,
                        post.publishedDateTime))
                .from(post)
                .leftJoin(post.author, account)
                .where(containsSearchKeyword(searchKeyword))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }

    private BooleanExpression containsSearchKeyword(String searchKeyword) {
        return searchKeyword != null ? post.title.contains(searchKeyword) : null;
    }


}
