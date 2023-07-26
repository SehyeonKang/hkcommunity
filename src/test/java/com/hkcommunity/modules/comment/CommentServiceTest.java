package com.hkcommunity.modules.comment;

import com.hkcommunity.infra.AbstractContainerBaseTest;
import com.hkcommunity.infra.exception.AccountNotFoundException;
import com.hkcommunity.infra.exception.CommentNotFoundException;
import com.hkcommunity.infra.exception.PostNotFoundException;
import com.hkcommunity.modules.account.AccountRepository;
import com.hkcommunity.modules.comment.form.CommentDto;
import com.hkcommunity.modules.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.hkcommunity.factory.AccountFactory.createAccount;
import static com.hkcommunity.factory.CommentCreateRequestFactory.createCommentCreateRequest;
import static com.hkcommunity.factory.CommentCreateRequestFactory.createCommentCreateRequestWithParentId;
import static com.hkcommunity.factory.CommentFactory.createComment;
import static com.hkcommunity.factory.CommentFactory.createDeletedComment;
import static com.hkcommunity.factory.CommentReadConditionFactory.createCommentReadCondition;
import static com.hkcommunity.factory.PostFactory.createPost;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest extends AbstractContainerBaseTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    PostRepository postRepository;

    @Test
    void readAllTest() {
        // given
        given(commentRepository.findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAsc(anyLong()))
                .willReturn(
                        List.of(createComment(null),
                                createComment(null)
                        )
                );

        // when
        List<CommentDto> result = commentService.readAll(createCommentReadCondition());

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void readAllDeletedCommentTest() {
        // given
        given(commentRepository.findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAsc(anyLong()))
                .willReturn(
                        List.of(createDeletedComment(null),
                                createDeletedComment(null)
                        )
                );

        // when
        List<CommentDto> result = commentService.readAll(createCommentReadCondition());

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getContent()).isNull();
        assertThat(result.get(0).getAccount()).isNull();
    }

    @Test
    void createTest() {
        // given
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(createAccount()));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(createPost()));

        // when
        commentService.create(createCommentCreateRequest());

        // then
        verify(commentRepository).save(any());
    }

    @Test
    void createExceptionByAccountNotFoundTest() {
        // given
        given(accountRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> commentService.create(createCommentCreateRequest()))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void createExceptionByPostNotFoundTest() {
        // given
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(createAccount()));
        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> commentService.create(createCommentCreateRequest()))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    void createExceptionByCommentNotFoundTest() {
        // given
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(createAccount()));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(createPost()));
        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> commentService.create(createCommentCreateRequestWithParentId(1L)))
                .isInstanceOf(CommentNotFoundException.class);
    }


}