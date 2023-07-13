package com.hkcommunity.modules.account.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class PasswordForm {

    @NotBlank
    @Length(min = 8, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9~!@#.]{8,50}$")
    private String newPassword;

    @NotBlank
    @Length(min = 8, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9~!@#.]{8,50}$")
    private String newPasswordConfirm;
}
