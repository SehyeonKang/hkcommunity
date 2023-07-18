package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.CurrentAccount;
import com.hkcommunity.modules.post.form.PostForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @GetMapping("/announcement/write")
    public String newAnnouncementForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new PostForm());
        return "post/form";
    }

    @PostMapping("/announcement/write")
    public String newAnnouncementWrite(@CurrentAccount Account account, @Valid PostForm postForm, Errors errors) {
        if (errors.hasErrors()) {
            return "post/form";
        }

        Post newPost = postService.createNewPost(modelMapper.map(postForm, Post.class), account);
        return "redirect:/announcement";
    }
}
