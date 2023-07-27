package com.hkcommunity.modules.comment;

import com.hkcommunity.infra.dto.Response;
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

    @PostMapping("/api/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody CommentCreateRequest request) {
        commentService.create(request);
        return Response.success();
    }

    @PutMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response update(@PathVariable Long id, @Valid @RequestBody CommentUpdateRequest request) {
        commentService.update(id, request);
        return Response.success();
    }

    @DeleteMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        commentService.delete(id);
        return Response.success();
    }
}
