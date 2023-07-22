package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.CurrentAccount;
import com.hkcommunity.modules.post.form.BoardResponseForm;
import com.hkcommunity.modules.post.form.PostForm;
import com.hkcommunity.modules.post.form.PostResponseForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CustomPostRepository customPostRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/announcement/write")
    public String newAnnouncementForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new PostForm());
        return "post/form";
    }

    @PostMapping("/announcement/write")
    public String writeNewAnnouncement(@CurrentAccount Account account, @Valid PostForm postForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "post/form";
        }

        Post newPost = postService.createNewPost(modelMapper.map(postForm, Post.class), account);
        return "redirect:/announcement/" + newPost.getId();
    }

    @GetMapping("/announcement/{id}")
    public String viewAnnouncement(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        PostResponseForm postResponseForm = postService.getPost(id, account);

        model.addAttribute(account);
        model.addAttribute("post", postResponseForm);
        return "post/view";
    }

    @GetMapping("/announcement/{id}/edit")
    public String viewAnnouncementEdit(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Post post = postService.getPostToUpdate(account, id);
        model.addAttribute(account);
        model.addAttribute(post);
        model.addAttribute(modelMapper.map(post, PostForm.class));
        return "post/edit-form";
    }

    @PostMapping("/announcement/{id}/edit")
    public String updateAnnouncement(@CurrentAccount Account account, @PathVariable Long id,
                                     @Valid PostForm postForm, Errors errors, Model model, RedirectAttributes attributes) {
        Post post = postService.getPostToUpdate(account, id);

        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(post);
            return "post/edit-form";
        }

        postService.updatePost(post, postForm);
        attributes.addFlashAttribute("message", "게시글을 수정했습니다.");
        return "redirect:/announcement/" + id;
    }

    @GetMapping("/announcement/{id}/delete")
    public String viewAnnouncementDelete(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Post post = postService.getPostToDelete(account, id);
        model.addAttribute(account);
        model.addAttribute(post);
        return "post/delete-form";
    }

    @PostMapping("/announcement/{id}/delete")
    public String deleteAnnouncement(@CurrentAccount Account account, @PathVariable Long id) {
        Post post = postService.getPostToDelete(account, id);
        postService.deletePost(post);
        return "redirect:/";
    }

    @GetMapping("/announcement")
    public String viewAnnouncementList(String searchKeyword, Pageable pageable, Model model) {
        Page<BoardResponseForm> result = customPostRepository.selectPostList(searchKeyword, pageable);
        model.addAttribute("list", result);
        model.addAttribute("maxPage", 5);
        model.addAttribute("searchKeyword", searchKeyword);
        pageModelSetting(result, model);
        return "post/list";
    }

    private void pageModelSetting(Page<BoardResponseForm> result, Model model) {
        model.addAttribute("totalCount", result.getTotalElements());
        model.addAttribute("size", result.getPageable().getPageSize());
        model.addAttribute("number", result.getPageable().getPageNumber());
        model.addAttribute("totalPages", result.getTotalPages());
    }
}
