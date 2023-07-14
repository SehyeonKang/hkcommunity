package com.hkcommunity.modules.account.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class NicknameForm {

    @NotBlank
    @Length(min = 2, max = 20)
    @Pattern(regexp = "^[가-힣A-Za-z0-9]{2,20}$")
    private String nickname;
}
