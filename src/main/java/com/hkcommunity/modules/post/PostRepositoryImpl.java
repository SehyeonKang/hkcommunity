package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.post.form.*;
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
    public Page<BoardResponseForm> selectPostList(String boardCategory, String category, Pageable pageable) {
        List<BoardResponseForm> content = getPostList(boardCategory, category, pageable);
        Long count = getCount(boardCategory, category);
        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public Page<BoardResponseForm> selectPostListWithKeyword(String boardCategory, String category, String searchType, String searchKeyword, Pageable pageable) {
        List<BoardResponseForm> content = getPostListWithKeyword(boardCategory, category, searchType, searchKeyword, pageable);
        Long count = getCountWithKeyword(boardCategory, category, searchType, searchKeyword);
        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public Page<ProfilePostResponseForm> selectProfilePostList(String author, Pageable pageable) {
        List<ProfilePostResponseForm> content = getProfilePostList(author, pageable);
        Long count = getProfilePostCount(author);
        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public void addAuthor(Account account, Post post) {
        post.addAuthor(account);
    }

    @Override
    public void plusViewCount(Post post) {
        post.plusViewCount();
    }

    @Override
    public void minusViewCount(Post post) {
        post.minusViewCount();
    }

    @Override
    public void plusLikeCount(Post post) {
        post.plusLikeCount();
    }

    @Override
    public void minusLikeCount(Post post) {
        post.minusLikeCount();
    }

    @Override
    public void plusCommentCount(Post post) {
        post.plusCommentCount();
    }

    @Override
    public void minusCommentCount(Post post) {
        post.minusCommentCount();
    }

    private Long getCount(String boardCategory, String category) {
        Long count = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(checkBoardCategory(boardCategory),
                        checkPostCategory(category))
                .fetchOne();

        return count;
    }

    private Long getCountWithKeyword(String boardCategory, String category, String searchType, String searchKeyword) {
        Long count = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(containsSearchKeyword(searchType, searchKeyword),
                        checkBoardCategory(boardCategory),
                        checkPostCategory(category))
                .fetchOne();

        return count;
    }

    private Long getProfilePostCount(String author) {
        Long count = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(post.author.nickname.eq(author))
                .fetchOne();

        return count;
    }

    private List<BoardResponseForm> getPostList(String boardCategory, String category, Pageable pageable) {

        List<BoardResponseForm> content = jpaQueryFactory
                .select(new QBoardResponseForm(
                        post.id,
                        post.viewCount,
                        post.likeCount,
                        post.commentCount,
                        post.title,
                        post.postCategory,
                        account.nickname,
                        post.publishedDateTime))
                .from(post)
                .leftJoin(post.author, account)
                .where(checkBoardCategory(boardCategory),
                        checkPostCategory(category))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }

    private List<BoardResponseForm> getPostListWithKeyword(String boardCategory, String category, String searchType, String searchKeyword, Pageable pageable) {

        List<BoardResponseForm> content = jpaQueryFactory
                .select(new QBoardResponseForm(
                        post.id,
                        post.viewCount,
                        post.likeCount,
                        post.commentCount,
                        post.title,
                        post.postCategory,
                        account.nickname,
                        post.publishedDateTime))
                .from(post)
                .leftJoin(post.author, account)
                .where(containsSearchKeyword(searchType, searchKeyword),
                        checkBoardCategory(boardCategory),
                        checkPostCategory(category))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }

    private List<ProfilePostResponseForm> getProfilePostList(String author, Pageable pageable) {
        List<ProfilePostResponseForm> content = jpaQueryFactory
                .select(new QProfilePostResponseForm(
                        post.id,
                        post.viewCount,
                        post.title,
                        post.boardCategory,
                        post.publishedDateTime))
                .from(post)
                .where(post.author.nickname.eq(author))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }

    private BooleanExpression containsSearchKeyword(String searchType, String searchKeyword) {

        if (searchType.equals(PostSearchType.ALL.getValue())) {
            return searchKeyword != null ? 
                    post.title.contains(searchKeyword)
                            .or(post.content.contains(searchKeyword))
                            .or(post.author.nickname.contains(searchKeyword))
                    : null;

        } else if (searchType.equals(PostSearchType.TITLE.getValue())) {
            return searchKeyword != null ? post.title.contains(searchKeyword) : null;

        } else if (searchType.equals(PostSearchType.CONTENT.getValue())) {
            return searchKeyword != null ? post.content.contains(searchKeyword) : null;

        } else if (searchType.equals(PostSearchType.TITCON.getValue())) {
            return searchKeyword != null ?
                    post.title.contains(searchKeyword)
                            .or(post.content.contains(searchKeyword))
                    :null;

        } else if (searchType.equals(PostSearchType.AUTHOR.getValue())) {
            return searchKeyword != null ? post.author.nickname.contains(searchKeyword) : null;
        }

        return null;
    }

    private BooleanExpression checkBoardCategory(String boardCategory) {
         return post.boardCategory.eq(boardCategory);
    }

    private BooleanExpression checkPostCategory(String category) {
        return category != null ? post.postCategory.eq(category) : null;
    }
}
