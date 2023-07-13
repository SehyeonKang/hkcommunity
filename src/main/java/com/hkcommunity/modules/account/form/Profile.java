package com.hkcommunity.modules.account.form;

import com.hkcommunity.modules.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class Profile {

    @Length(max = 50)
    private String introduction;

    private String profileImage;

    public Profile(Account account) {
        this.introduction = account.getIntroduction();
        this.profileImage = account.getProfileImage();
    }
}
