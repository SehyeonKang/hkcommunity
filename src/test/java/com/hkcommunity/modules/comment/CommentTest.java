package com.hkcommunity.modules.comment;

import com.hkcommunity.infra.AbstractContainerBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.hkcommunity.factory.CommentFactory.createComment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ActiveProfiles("test")
public class CommentTest extends AbstractContainerBaseTest {

    @DisplayName("delete 상태 변경 테스트")
    @Test
    void deleteTest() {
        // given
        Comment comment = createComment(null);
        boolean beforeDeleted = comment.isDeleted();

        // when
        comment.delete();

        // then
        boolean afterDeleted = comment.isDeleted();
        assertThat(beforeDeleted).isFalse();
        assertThat(afterDeleted).isTrue();
    }

    @DisplayName("삭제 가능한 부모 댓글 확인")
    @Test
    void findDeletableCommentWhenExistsTest() {
        // given

        // root 1
        // 1 -> 2(del), 2(del) -> 3(del), 2(del) -> 4, 3(del) -> 5
        Comment comment1 = createComment(null);
        Comment comment2 = createComment(comment1);
        Comment comment3 = createComment(comment2);
        Comment comment4 = createComment(comment2);
        Comment comment5 = createComment(comment3);

        comment2.delete();
        comment3.delete();

        setField(comment1, "children", List.of(comment2));
        setField(comment2, "children", List.of(comment3, comment4));
        setField(comment3, "children", List.of(comment5));
        setField(comment4, "children", List.of());
        setField(comment5, "children", List.of());

        // when
        Optional<Comment> deletableComment = comment5.findDeletableComment();

        // then
        assertThat(deletableComment).containsSame(comment3);
    }

    @Test
    void findDeletableCommentWhenNotExistsTest() {
        // given

        // root 1
        // 1 -> 2, 2 -> 3
        Comment comment1 = createComment(null);
        Comment comment2 = createComment(comment1);
        Comment comment3 = createComment(comment2);

        setField(comment1, "children", List.of(comment2));
        setField(comment2, "children", List.of(comment3));
        setField(comment3, "children", List.of());

        // when
        Optional<Comment> deletableComment = comment2.findDeletableComment();

        // then
        assertThat(deletableComment).isEmpty();
    }

}
