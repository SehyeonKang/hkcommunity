package com.hkcommunity.modules.post;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.CurrentAccount;
import com.hkcommunity.modules.like.LikeService;
import com.hkcommunity.modules.like.form.LikeForm;
import com.hkcommunity.modules.like.form.LikeResponseForm;
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
    private final LikeService likeService;
    private final ModelMapper modelMapper;

    @GetMapping("/announcement/write")
    public String newAnnouncementForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new PostForm());
        return "post/announcement/form";
    }

    @PostMapping("/announcement/write")
    public String writeNewAnnouncement(@CurrentAccount Account account, @Valid PostForm postForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "post/announcement/form";
        }

        Post newPost = postService.createNewPost(modelMapper.map(postForm, Post.class), account);
        return "redirect:/announcement/" + newPost.getId();
    }

    @GetMapping("/announcement/{id}")
    public String viewAnnouncement(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        model.addAttribute(account);
        viewPostSetting(account, id, model);
        return "post/announcement/view";
    }

    @GetMapping("/announcement/{id}/edit")
    public String viewAnnouncementEdit(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Post post = postService.getPostToUpdate(account, id);
        model.addAttribute(account);
        model.addAttribute(post);
        model.addAttribute(modelMapper.map(post, PostForm.class));
        return "post/announcement/edit-form";
    }

    @PostMapping("/announcement/{id}/edit")
    public String updateAnnouncement(@CurrentAccount Account account, @PathVariable Long id,
                                     @Valid PostForm postForm, Errors errors, Model model, RedirectAttributes attributes) {
        Post post = postService.getPostToUpdate(account, id);

        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(post);
            return "post/announcement/edit-form";
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
        return "post/announcement/delete-form";
    }

    @PostMapping("/announcement/{id}/delete")
    public String deleteAnnouncement(@CurrentAccount Account account, @PathVariable Long id) {
        Post post = postService.getPostToDelete(account, id);
        postService.deletePost(post);
        return "redirect:/announcement";
    }

    @GetMapping("/announcement")
    public String viewAnnouncementList(String category, String searchType, String searchKeyword, Pageable pageable, Model model) {
        Page<BoardResponseForm> result = postService.selectPostList("announcement", category, searchType, searchKeyword, pageable);
        model.addAttribute("list", result);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("searchType", searchType);
        model.addAttribute("category", category);
        pageModelSetting(result, model);

        return "post/announcement/list";
    }

    @GetMapping("/dimension/write")
    public String newDimensionForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new PostForm());
        return "post/dimension/form";
    }

    @PostMapping("/dimension/write")
    public String writeNewDimension(@CurrentAccount Account account, @Valid PostForm postForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "post/dimension/form";
        }

        Post newPost = postService.createNewPost(modelMapper.map(postForm, Post.class), account);
        return "redirect:/dimension/" + newPost.getId();
    }

    @GetMapping("/dimension/{id}")
    public String viewDimension(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        model.addAttribute(account);
        viewPostSetting(account, id, model);
        return "post/dimension/view";
    }

    @GetMapping("/dimension/{id}/edit")
    public String viewDimensionEdit(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Post post = postService.getPostToUpdate(account, id);
        model.addAttribute(account);
        model.addAttribute(post);
        model.addAttribute(modelMapper.map(post, PostForm.class));
        return "post/dimension/edit-form";
    }

    @PostMapping("/dimension/{id}/edit")
    public String updateDimension(@CurrentAccount Account account, @PathVariable Long id,
                                     @Valid PostForm postForm, Errors errors, Model model, RedirectAttributes attributes) {
        Post post = postService.getPostToUpdate(account, id);

        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(post);
            return "post/dimension/edit-form";
        }

        postService.updatePost(post, postForm);
        attributes.addFlashAttribute("message", "게시글을 수정했습니다.");
        return "redirect:/dimension/" + id;
    }

    @GetMapping("/dimension/{id}/delete")
    public String viewDimensionDelete(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Post post = postService.getPostToDelete(account, id);
        model.addAttribute(account);
        model.addAttribute(post);
        return "post/dimension/delete-form";
    }

    @PostMapping("/dimension/{id}/delete")
    public String deleteDimension(@CurrentAccount Account account, @PathVariable Long id) {
        Post post = postService.getPostToDelete(account, id);
        postService.deletePost(post);
        return "redirect:/dimension";
    }

    @GetMapping("/dimension")
    public String viewDimensionList(String category, String searchType, String searchKeyword, Pageable pageable, Model model) {
        Page<BoardResponseForm> result = postService.selectPostList("dimension", category, searchType, searchKeyword, pageable);
        model.addAttribute("list", result);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("searchType", searchType);
        model.addAttribute("category", category);
        pageModelSetting(result, model);

        return "post/dimension/list";
    }

    @GetMapping("/arena/write")
    public String newArenaForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new PostForm());
        return "post/arena/form";
    }

    @PostMapping("/arena/write")
    public String writeNewArena(@CurrentAccount Account account, @Valid PostForm postForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "post/arena/form";
        }

        Post newPost = postService.createNewPost(modelMapper.map(postForm, Post.class), account);
        return "redirect:/arena/" + newPost.getId();
    }

    @GetMapping("/arena/{id}")
    public String viewArena(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        model.addAttribute(account);
        viewPostSetting(account, id, model);
        return "post/arena/view";
    }

    @GetMapping("/arena/{id}/edit")
    public String viewArenaEdit(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Post post = postService.getPostToUpdate(account, id);
        model.addAttribute(account);
        model.addAttribute(post);
        model.addAttribute(modelMapper.map(post, PostForm.class));
        return "post/arena/edit-form";
    }

    @PostMapping("/arena/{id}/edit")
    public String updateArena(@CurrentAccount Account account, @PathVariable Long id,
                                  @Valid PostForm postForm, Errors errors, Model model, RedirectAttributes attributes) {
        Post post = postService.getPostToUpdate(account, id);

        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(post);
            return "post/arena/edit-form";
        }

        postService.updatePost(post, postForm);
        attributes.addFlashAttribute("message", "게시글을 수정했습니다.");
        return "redirect:/arena/" + id;
    }

    @GetMapping("/arena/{id}/delete")
    public String viewArenaDelete(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Post post = postService.getPostToDelete(account, id);
        model.addAttribute(account);
        model.addAttribute(post);
        return "post/arena/delete-form";
    }

    @PostMapping("/arena/{id}/delete")
    public String deleteArena(@CurrentAccount Account account, @PathVariable Long id) {
        Post post = postService.getPostToDelete(account, id);
        postService.deletePost(post);
        return "redirect:/arena";
    }

    @GetMapping("/arena")
    public String viewArenaList(String category, String searchType, String searchKeyword, Pageable pageable, Model model) {
        Page<BoardResponseForm> result = postService.selectPostList("arena", category, searchType, searchKeyword, pageable);
        model.addAttribute("list", result);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("searchType", searchType);
        model.addAttribute("category", category);
        pageModelSetting(result, model);

        return "post/arena/list";
    }

    @GetMapping("/realm/write")
    public String newRealmForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new PostForm());
        return "post/realm/form";
    }

    @PostMapping("/realm/write")
    public String writeNewRealm(@CurrentAccount Account account, @Valid PostForm postForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "post/realm/form";
        }

        Post newPost = postService.createNewPost(modelMapper.map(postForm, Post.class), account);
        return "redirect:/realm/" + newPost.getId();
    }

    @GetMapping("/realm/{id}")
    public String viewRealm(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        model.addAttribute(account);
        viewPostSetting(account, id, model);
        return "post/realm/view";
    }

    @GetMapping("/realm/{id}/edit")
    public String viewRealmEdit(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Post post = postService.getPostToUpdate(account, id);
        model.addAttribute(account);
        model.addAttribute(post);
        model.addAttribute(modelMapper.map(post, PostForm.class));
        return "post/realm/edit-form";
    }

    @PostMapping("/realm/{id}/edit")
    public String updateRealm(@CurrentAccount Account account, @PathVariable Long id,
                              @Valid PostForm postForm, Errors errors, Model model, RedirectAttributes attributes) {
        Post post = postService.getPostToUpdate(account, id);

        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(post);
            return "post/realm/edit-form";
        }

        postService.updatePost(post, postForm);
        attributes.addFlashAttribute("message", "게시글을 수정했습니다.");
        return "redirect:/realm/" + id;
    }

    @GetMapping("/realm/{id}/delete")
    public String viewRealmDelete(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Post post = postService.getPostToDelete(account, id);
        model.addAttribute(account);
        model.addAttribute(post);
        return "post/realm/delete-form";
    }

    @PostMapping("/realm/{id}/delete")
    public String deleteRealm(@CurrentAccount Account account, @PathVariable Long id) {
        Post post = postService.getPostToDelete(account, id);
        postService.deletePost(post);
        return "redirect:/realm";
    }

    @GetMapping("/realm")
    public String viewRealmList(String category, String searchType, String searchKeyword, Pageable pageable, Model model) {
        Page<BoardResponseForm> result = postService.selectPostList("realm", category, searchType, searchKeyword, pageable);
        model.addAttribute("list", result);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("searchType", searchType);
        model.addAttribute("category", category);
        pageModelSetting(result, model);

        return "post/realm/list";
    }

    private void viewPostSetting(Account account, Long id, Model model) {
        Post post = postService.getPost(id);
        PostResponseForm postResponseForm = postService.getPostResponseForm(id, account);
        LikeResponseForm likeResponseForm = likeService.getLikeInfo(new LikeForm(account, post));
        boolean pushedCheck = likeResponseForm.isPushedCheck();
        Long likeCount = likeResponseForm.getPostLikeNum();

        model.addAttribute("post", postResponseForm);
        model.addAttribute("likeCheck", pushedCheck);
        model.addAttribute("likeCount", likeCount);
    }

    private void pageModelSetting(Page<BoardResponseForm> result, Model model) {
        model.addAttribute("maxPage", 5);
        model.addAttribute("totalCount", result.getTotalElements());
        model.addAttribute("size", result.getPageable().getPageSize());
        model.addAttribute("number", result.getPageable().getPageNumber());
        model.addAttribute("totalPages", result.getTotalPages());
    }


}
