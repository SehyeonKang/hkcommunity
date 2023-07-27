package com.hkcommunity.modules.comment.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hkcommunity.infra.helper.NestedConvertHelper;
import com.hkcommunity.modules.account.form.AccountDto;
import com.hkcommunity.modules.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private AccountDto account;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdDateTime;
    private List<CommentDto> children;

    public static List<CommentDto> toDtoList(List<Comment> comments) {
        NestedConvertHelper helper = NestedConvertHelper.newInstance(
                comments,
                c -> new CommentDto(c.getId(), c.isDeleted() ? null : c.getContent(), c.isDeleted() ? null : AccountDto.toDto(c.getAccount()), c.getCreatedDateTime(), new ArrayList<>()),
                c -> c.getParent(),
                c -> c.getId(),
                d -> d.getChildren());
        return helper.convert();
    }
}
