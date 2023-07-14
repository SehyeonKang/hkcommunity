package com.hkcommunity.modules.account.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class Profile {

    @Length(max = 50)
    private String introduction;

    private String profileImage;

}
