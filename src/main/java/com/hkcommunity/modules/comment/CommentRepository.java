package com.hkcommunity.modules.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {

    @Query("select c from Comment c left join fetch c.parent where c.id = :id")
    Optional<Comment> findWithParentById(@Param("id") Long id);

    @Query("select c from Comment c join fetch c.account left join fetch c.parent where c.post.id = :postId order by c.parent.id asc nulls first, c.id asc ")
    List<Comment> findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAsc(@Param("postId") Long postId);
}
