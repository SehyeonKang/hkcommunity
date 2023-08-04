package com.hkcommunity.modules.comment;

import com.hkcommunity.infra.dto.Response;
import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.CurrentAccount;
import com.hkcommunity.modules.comment.form.CommentCreateRequest;
import com.hkcommunity.modules.comment.form.CommentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public Response readAll(@PathVariable Long postId) {
        return Response.success(commentService.readAll(postId));
    }

    @GetMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response readOne(@PathVariable Long id) {
        return Response.success(commentService.readOne(id));
    }

    @PostMapping("/api/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@CurrentAccount Account account, @Valid @RequestBody CommentCreateRequest request) {
        commentService.create(account, request);
        return Response.success();
    }

    @PatchMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response update(@CurrentAccount Account account, @PathVariable Long id, @Valid @RequestBody CommentUpdateRequest request) {
        commentService.update(account, id, request);
        return Response.success();
    }

    @DeleteMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@CurrentAccount Account account, @PathVariable Long id) {
        commentService.delete(account, id);
        return Response.success();
    }
}
