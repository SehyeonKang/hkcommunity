package com.hkcommunity.modules.account;

import com.hkcommunity.modules.account.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final AccountService accountService;


    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String userId = withAccount.value();

        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setUserId(userId);
        signUpForm.setPassword("12345678");
        signUpForm.setNickname(userId);
        signUpForm.setEmail(userId + "@email.com");
        accountService.processNewAccount(signUpForm);

        UserDetails principal = accountService.loadUserByUsername(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
