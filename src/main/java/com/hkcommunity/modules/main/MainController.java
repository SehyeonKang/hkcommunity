package com.hkcommunity.modules.main;

import com.hkcommunity.modules.account.Account;
import com.hkcommunity.modules.account.CurrentAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(@CurrentAccount Account account, Model model) {
        if (account != null) {
            model.addAttribute(account);
        }

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
