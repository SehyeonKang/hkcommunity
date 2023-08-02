package com.hkcommunity.modules.account.form;

import com.hkcommunity.modules.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AccountDto {

    private Long id;
    private String email;
    private String userId;
    private String nickname;
    private String profileImage;

    public static AccountDto toDto(Account account) {
        return new AccountDto(account.getId(), account.getEmail(), account.getUserId(), account.getNickname(), account.getProfileImage());
    }

    public static AccountDto empty() {
        return new AccountDto(null, "", "", "", "");
    }
}
