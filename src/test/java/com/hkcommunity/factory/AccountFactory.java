package com.hkcommunity.factory;

import com.hkcommunity.modules.account.Account;

public class AccountFactory {

    public static Account createAccount() {
        return new Account("test@email.com", "nickname", "userId", "12345678!");
    }

    public static Account createAccount(String userId, String password, String email, String nickname) {
        return new Account(userId, password, email, nickname);
    }


}
