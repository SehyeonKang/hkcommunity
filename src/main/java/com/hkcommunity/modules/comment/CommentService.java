package com.hkcommunity.modules.comment;

import com.hkcommunity.infra.exception.CommentNotFoundException;
import com.hkcommunity.modules.account.AccountRepository;
import com.hkcommunity.modules.comment.form.CommentCreateRequest;
import com.hkcommunity.modules.comment.form.CommentDto;
import com.hkcommunity.modules.comment.form.CommentReadCondition;
import com.hkcommunity.modules.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> readAll(CommentReadCondition condition) {
        return CommentDto.toDtoList(
                commentRepository.findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAsc(condition.getPostId())
        );
    }

    public void create(CommentCreateRequest request) {
        commentRepository.save(CommentCreateRequest.toEntity(request, accountRepository, postRepository, commentRepository));
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comment.findDeletableComment().ifPresentOrElse(commentRepository::delete, comment::delete);
    }
}
