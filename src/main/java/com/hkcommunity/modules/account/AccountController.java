package com.hkcommunity.modules.account;

import com.hkcommunity.modules.account.form.SignUpForm;
import com.hkcommunity.modules.account.validator.SignUpFormValidator;
import com.hkcommunity.modules.comment.CommentService;
import com.hkcommunity.modules.comment.form.ProfileCommentResponseForm;
import com.hkcommunity.modules.post.PostService;
import com.hkcommunity.modules.post.form.ProfilePostResponseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;
    private final PostService postService;
    private final CommentService commentService;
    private final AccountRepository accountRepository;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute(new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return "account/sign-up";
        }

        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);

        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";

        if (account == null) {
            model.addAttribute("error", "wrong email");
            return view;
        }

        if (!account.isValidToken(token)) {
            model.addAttribute("error", "wrong email");
            return view;
        }

        accountService.completeSignUp(account);
        model.addAttribute("numberOfUser", accountRepository.count());
        model.addAttribute("nickname", account.getNickname());
        return view;
    }

    @GetMapping("/check-email")
    public String checkEmail(@CurrentAccount Account account, Model model) {
        model.addAttribute("email", account.getEmail());
        return "account/check-email";
    }

    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentAccount Account account, Model model) {
        if (!account.canSendConfirmEmail()) {
            model.addAttribute("error", "인증 이메일은 5분에 한번만 전송 가능합니다.");
            model.addAttribute("email", account.getEmail());
            return "account/check-email";
        }

        accountService.sendSignUpConfirmEmail(account);
        return "redirect:/";
    }

    @GetMapping("/profile/{nickname}")
    public String viewProfile(@PathVariable String nickname, Model model, @CurrentAccount Account account) {
        Account accountToView = accountService.getAccount(nickname);

        model.addAttribute(account);
        model.addAttribute(nickname);
        model.addAttribute("accountToView", accountToView);
        model.addAttribute("isOwner", accountToView.equals(account));
        return "account/profile";
    }

    @GetMapping("/profile/{nickname}/posts")
    public String viewProfilePosts(@PathVariable String nickname, Model model, @CurrentAccount Account account, Pageable pageable) {
        Account accountToView = accountService.getAccount(nickname);
        Page<ProfilePostResponseForm> result = postService.selectProfilePostList(nickname, pageable);

        model.addAttribute(account);
        model.addAttribute(nickname);
        model.addAttribute("accountToView", accountToView);
        model.addAttribute("isOwner", accountToView.equals(account));
        model.addAttribute("list", result);
        pagePostModelSetting(result, model);
        return "account/profile-posts";
    }

    @GetMapping("/profile/{nickname}/comments")
    public String viewProfileComments(@PathVariable String nickname, Model model, @CurrentAccount Account account, Pageable pageable) {
        Account accountToView = accountService.getAccount(nickname);
        Page<ProfileCommentResponseForm> result = commentService.selectProfileCommentList(nickname, pageable);

        model.addAttribute(account);
        model.addAttribute(nickname);
        model.addAttribute("accountToView", accountToView);
        model.addAttribute("isOwner", accountToView.equals(account));
        model.addAttribute("list", result);
        pageCommentModelSetting(result, model);
        return "account/profile-comments";
    }

    @GetMapping("/email-login")
    public String emailLoginForm() {
        return "account/email-login";
    }

    @PostMapping("/email-login")
    public String sendEmailLoginLink(String email, Model model, RedirectAttributes attributes) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            model.addAttribute("error", "유효한 이메일 주소가 아닙니다.");
            return "account/email-login";
        }

        if (!account.canSendConfirmEmail()) {
            model.addAttribute("error", "이메일 로그인은 5분 뒤에 사용할 수 있습니다.");
            return "account/email-login";
        }

        accountService.sendLoginLink(account);
        attributes.addFlashAttribute("message", "이메일 인증 메일을 발송했습니다.");
        return "redirect:/email-login";
    }

    @GetMapping("/login-by-email")
    public String loginByEmail(String token, String email, Model model) {
        Account account = accountRepository.findByEmail(email);
        String view = "account/logged-in-by-email";

        if (account == null || !account.isValidToken(token)) {
            model.addAttribute("error", "로그안 할 수 없습니다.");
            return view;
        }

        accountService.login(account);
        return view;
    }

    private void pagePostModelSetting(Page<ProfilePostResponseForm> result, Model model) {
        model.addAttribute("maxPage", 10);
        model.addAttribute("totalCount", result.getTotalElements());
        model.addAttribute("size", result.getPageable().getPageSize());
        model.addAttribute("number", result.getPageable().getPageNumber());
        model.addAttribute("totalPages", result.getTotalPages());
    }

    private void pageCommentModelSetting(Page<ProfileCommentResponseForm> result, Model model) {
        model.addAttribute("maxPage", 10);
        model.addAttribute("totalCount", result.getTotalElements());
        model.addAttribute("size", result.getPageable().getPageSize());
        model.addAttribute("number", result.getPageable().getPageNumber());
        model.addAttribute("totalPages", result.getTotalPages());
    }
}
