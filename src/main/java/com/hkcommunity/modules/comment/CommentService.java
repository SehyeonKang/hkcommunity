package com.hkcommunity.modules.comment;

import com.hkcommunity.infra.exception.CommentNotFoundException;
import com.hkcommunity.modules.account.AccountRepository;
import com.hkcommunity.modules.account.form.AccountDto;
import com.hkcommunity.modules.comment.form.CommentCreateRequest;
import com.hkcommunity.modules.comment.form.CommentDto;
import com.hkcommunity.modules.comment.form.CommentUpdateRequest;
import com.hkcommunity.modules.comment.form.CommentUpdateResponse;
import com.hkcommunity.modules.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> readAll(Long postId) {
        return CommentDto.toDtoList(
                commentRepository.findAllWithMemberAndParentByPostIdOrderByParentIdAscNullsFirstCommentIdAsc(postId)
        );
    }

    @Transactional(readOnly = true)
    public CommentUpdateResponse readOne(Long id) {
        Optional<Comment> commentWrapper = commentRepository.findById(id);
        Comment comment = commentWrapper.get();
        AccountDto accountDto = AccountDto.toDto(comment.getAccount());

        CommentUpdateResponse commentUpdateResponse = CommentUpdateResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .account(accountDto)
                .createdDateTime(comment.getCreatedDateTime())
                .build();

        return commentUpdateResponse;
    }

    public void create(CommentCreateRequest request) {
        commentRepository.save(CommentCreateRequest.toEntity(request, accountRepository, postRepository, commentRepository));
    }

    public void update(Long id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comment.update(request);
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comment.findDeletableComment().ifPresentOrElse(commentRepository::delete, comment::delete);
    }
}
