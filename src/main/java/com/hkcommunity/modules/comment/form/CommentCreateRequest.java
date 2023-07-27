package com.hkcommunity.modules.comment.form;

import com.hkcommunity.infra.exception.AccountNotFoundException;
import com.hkcommunity.infra.exception.CommentNotFoundException;
import com.hkcommunity.infra.exception.PostNotFoundException;
import com.hkcommunity.modules.account.AccountRepository;
import com.hkcommunity.modules.comment.Comment;
import com.hkcommunity.modules.comment.CommentRepository;
import com.hkcommunity.modules.post.PostRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Optional;

@Data
@ApiModel(value = "댓글 생성 요청")
@AllArgsConstructor @NoArgsConstructor
public class CommentCreateRequest {

    @ApiModelProperty(value = "댓글", notes = "댓글을 입력해주세요", required = true, example = "my comment")
    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;

    @ApiModelProperty(value = "게시글 아이디", notes = "게시글 아이디를 입력해주세요", example = "7")
    @NotNull(message = "게시글 아이디를 입력해주세요.")
    @Positive(message = "올바른 게시글 아이디를 입력해주세요.")
    private Long postId;

    @ApiModelProperty(hidden = true)
    private Long accountId;

    @ApiModelProperty(value = "부모 댓글 아이디", notes = "부모 댓글 아이디를 입력해주세요", example = "7")
    private Long parentId;

    public static Comment toEntity(CommentCreateRequest req, AccountRepository accountRepository, PostRepository postRepository, CommentRepository commentRepository) {
        return new Comment(
                req.content,
                accountRepository.findById(req.accountId).orElseThrow(AccountNotFoundException::new),
                postRepository.findById(req.postId).orElseThrow(PostNotFoundException::new),
                Optional.ofNullable(req.parentId)
                        .map(id -> commentRepository.findById(id).orElseThrow(CommentNotFoundException::new))
                        .orElse(null)
        );
    }
}
