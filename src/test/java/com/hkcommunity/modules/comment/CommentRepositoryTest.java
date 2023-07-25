package com.hkcommunity.modules.comment;

import com.hkcommunity.infra.AbstractContainerBaseTest;
import com.hkcommunity.infra.config.QuerydslConfig;
import com.hkcommunity.infra.exception.CommentNotFoundException;
import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.AccountRepository;
import com.hkcommunity.modules.post.Post;
import com.hkcommunity.modules.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.hkcommunity.factory.AccountFactory.createAccount;
import static com.hkcommunity.factory.CommentFactory.createComment;
import static com.hkcommunity.factory.PostFactory.createPost;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
public class CommentRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @PersistenceContext
    EntityManager em;

    Account account;
    Post post;

    @BeforeEach
    void beforeEach() {
        Account newAccount = createAccount();
        account = accountRepository.save(newAccount);
        post = postRepository.save(createPost(newAccount));
    }

    @Test
    void createAndReadTest() {
        // given
        Comment comment = commentRepository.save(createComment(account, post, null));
        clear();

        // when
        Comment foundComment = commentRepository.findById(comment.getId()).orElseThrow(CommentNotFoundException::new);

        // then
        assertThat(foundComment.getId()).isEqualTo(comment.getId());
    }

    @Test
    void deleteTest() {
        // given
        Comment comment = commentRepository.save(createComment(account, post, null));
        clear();

        // when
        commentRepository.deleteById(comment.getId());

        // then
        assertThat(commentRepository.findById(comment.getId())).isEmpty();
    }

    @Test
    void deleteCascadeByParentTest() {
        // given
        Comment parent = commentRepository.save(createComment(account, post, null));
        Comment child = commentRepository.save(createComment(account, post, parent));
        clear();

        // when
        commentRepository.deleteById(parent.getId());
        clear();

        // then
        assertThat(commentRepository.findById(child.getId())).isEmpty();
    }

    @Test
    void getChildrenTest() {
        // given
        Comment parent = commentRepository.save(createComment(account, post, null));
        commentRepository.save(createComment(account, post, parent));
        commentRepository.save(createComment(account, post, parent));
        clear();

        // when
        Comment comment = commentRepository.findById(parent.getId()).orElseThrow(CommentNotFoundException::new);

        // then
        assertThat(comment.getChildren().size()).isEqualTo(2);
    }

    @Test
    void findWithParentByIdTest() {
        // given
        Comment parent = commentRepository.save(createComment(account, post, null));
        Comment child = commentRepository.save(createComment(account, post, parent));
        clear();

        // when
        Comment comment = commentRepository.findWithParentById(child.getId()).orElseThrow(CommentNotFoundException::new);

        // then
        assertThat(comment.getParent()).isNotNull();
    }

    @Test
    void deleteCommentTest() {
        // given

        // root 1
        // 1 -> 2(del), 2(del) -> 3(del), 2(del) -> 4, 3(del) -> 5
        Comment comment1 = commentRepository.save(createComment(account, post, null));
        Comment comment2 = commentRepository.save(createComment(account, post, comment1));
        Comment comment3 = commentRepository.save(createComment(account, post, comment2));
        Comment comment4 = commentRepository.save(createComment(account, post, comment2));
        Comment comment5 = commentRepository.save(createComment(account, post, comment3));

        comment2.delete();
        comment3.delete();
        clear();

        //when
        Comment comment = commentRepository.findWithParentById(comment5.getId()).orElseThrow(CommentNotFoundException::new);
        comment.findDeletableComment().ifPresentOrElse(c -> commentRepository.delete(c), () -> comment5.delete());
        clear();

        //then
        List<Comment> comments = commentRepository.findAll();
        List<Long> commentIds = comments.stream().map(c -> c.getId()).collect(toList());
        assertThat(commentIds.size()).isEqualTo(3);
        assertThat(commentIds).contains(comment1.getId(), comment2.getId(), comment4.getId());
    }

    @Test
    void deleteCommentQueryLogTest() {
        // given

        // 1(del) -> 2(del) -> 3(del) -> 4(del) -> 5
        Comment comment1 = commentRepository.save(createComment(account, post, null));
        Comment comment2 = commentRepository.save(createComment(account, post, comment1));
        Comment comment3 = commentRepository.save(createComment(account, post, comment2));
        Comment comment4 = commentRepository.save(createComment(account, post, comment3));
        Comment comment5 = commentRepository.save(createComment(account, post, comment4));

        comment1.delete();
        comment2.delete();
        comment3.delete();
        comment4.delete();
        clear();

        // when
        Comment comment = commentRepository.findWithParentById(comment5.getId()).orElseThrow(CommentNotFoundException::new);
        comment.findDeletableComment().ifPresentOrElse(c -> commentRepository.delete(c), () -> comment5.delete());
        clear();

        //then
        List<Comment> comments = commentRepository.findAll();
        List<Long> commentIds = comments.stream().map(c -> c.getId()).collect(toList());
        assertThat(commentIds.size()).isEqualTo(0);
    }

    @Test
    void findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAscTest() {
        //given

        // 1    NULL
        // 2    1
        // 3    1
        // 4    2
        // 5    2
        // 6    4
        // 7    3
        // 8    NULL
        Comment comment1 = commentRepository.save(createComment(account, post, null));
        Comment comment2 = commentRepository.save(createComment(account, post, comment1));
        Comment comment3 = commentRepository.save(createComment(account, post, comment1));
        Comment comment4 = commentRepository.save(createComment(account, post, comment2));
        Comment comment5 = commentRepository.save(createComment(account, post, comment2));
        Comment comment6 = commentRepository.save(createComment(account, post, comment4));
        Comment comment7 = commentRepository.save(createComment(account, post, comment3));
        Comment comment8 = commentRepository.save(createComment(account, post, null));
        clear();

        //when
        List<Comment> result = commentRepository.findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAsc(post.getId());

        //then

        // 1    NULL
        // 8    NULL
        // 2    1
        // 3    1
        // 4    2
        // 5    2
        // 7    3
        // 6    4
        assertThat(result.size()).isEqualTo(8);
        assertThat(result.get(0).getId()).isEqualTo(comment1.getId());
        assertThat(result.get(1).getId()).isEqualTo(comment8.getId());
        assertThat(result.get(2).getId()).isEqualTo(comment2.getId());
        assertThat(result.get(3).getId()).isEqualTo(comment3.getId());
        assertThat(result.get(4).getId()).isEqualTo(comment4.getId());
        assertThat(result.get(5).getId()).isEqualTo(comment5.getId());
        assertThat(result.get(6).getId()).isEqualTo(comment7.getId());
        assertThat(result.get(7).getId()).isEqualTo(comment6.getId());
    }

    void clear() {
        em.flush();
        em.clear();
    }
}
