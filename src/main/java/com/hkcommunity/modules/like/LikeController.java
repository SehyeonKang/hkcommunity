package com.hkcommunity.modules.like;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.CurrentAccount;
import com.hkcommunity.modules.like.form.LikeForm;
import com.hkcommunity.modules.post.Post;
import com.hkcommunity.modules.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final PostService postService;

    @PostMapping("/like/{postId}")
    public String pushLikeButton(@CurrentAccount Account account, @PathVariable Long postId) {
        Post post = postService.getPost(postId);
        LikeForm likeForm = new LikeForm(account, post);
        if (account != null) {
            likeService.pushLikeButton(likeForm);
        }

        String redirectLink = "redirect:/" + post.getBoardCategory() + "/" + postId;

        return redirectLink;
    }
}
