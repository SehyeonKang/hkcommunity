package com.hkcommunity.modules.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpForm {

    @NotBlank
    @Length(min = 4, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$")
    private String userId;

    @NotBlank
    @Length(min = 8, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9~!@#.]{8,50}$")
    private String password;

    @NotBlank
    @Length(min = 2, max = 20)
    @Pattern(regexp = "^[가-힣A-Za-z0-9]{2,20}$")
    private String nickname;

    @Email
    @NotBlank
    private String email;
}
