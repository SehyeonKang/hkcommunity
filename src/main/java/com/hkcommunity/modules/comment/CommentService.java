package com.hkcommunity.modules.comment;

import com.hkcommunity.infra.exception.CommentNotFoundException;
import com.hkcommunity.modules.account.AccountRepository;
import com.hkcommunity.modules.account.form.AccountDto;
import com.hkcommunity.modules.comment.form.*;
import com.hkcommunity.modules.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ApplicationEventPublisher eventPublisher;

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

    @Transactional(readOnly = true)
    public Page<ProfileCommentResponseForm> selectProfileCommentList(String author, Pageable pageable) {
        return commentRepository.selectProfileCommentList(author, pageable);
    }

    public void create(CommentCreateRequest request) {
        Comment comment = commentRepository.save(CommentCreateRequest.toEntity(request, accountRepository, postRepository, commentRepository));
        postRepository.plusCommentCount(comment.getPost());
        comment.publishCreatedEvent(eventPublisher);
    }

    public void update(Long id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comment.update(request);
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        postRepository.minusCommentCount(comment.getPost());
        comment.findDeletableComment().ifPresentOrElse(commentRepository::delete, comment::delete);
    }
}
