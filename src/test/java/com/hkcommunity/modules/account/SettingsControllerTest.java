package com.hkcommunity.modules.account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static com.hkcommunity.modules.account.SettingsController.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SettingsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @WithAccount("test")
    @DisplayName("프로필 수정 폼")
    @Test
    void updateProfileForm() throws Exception {
        mockMvc.perform(get(ROOT + SETTINGS + PROFILE))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"));
    }

    @WithAccount("test")
    @DisplayName("프로필 수정하기 - 입력값 정상")
    @Test
    void updateProfile_with_correct_input() throws Exception{
        String introduction = "소개를 수정하기";
        mockMvc.perform(post(ROOT + SETTINGS + PROFILE)
                        .param("introduction", introduction)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ROOT + SETTINGS + PROFILE))
                .andExpect(flash().attributeExists("message"));

        Account test = accountRepository.findByUserId("test");
        assertEquals(introduction, test.getIntroduction());
    }

    @WithAccount("test")
    @DisplayName("프로필 수정하기 - 입력값 오류")
    @Test
    void updateProfile_with_wrong_input() throws Exception {
        String introduction = "소개를 너무 길게 수정하는 경우 - 소개를 너무 길게 수정하는 경우 - 소개를 너무 길게 수정하는 경우 - 소개를 너무 길게 수정하는 경우";
        mockMvc.perform(post(ROOT + SETTINGS + PROFILE)
                        .param("introduction", introduction)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(ROOT + SETTINGS + PROFILE))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().hasErrors());

        Account test = accountRepository.findByUserId("test");
        assertNull(test.getIntroduction());
    }

    @WithAccount("test")
    @DisplayName("비밀번호 수정 폼")
    @Test
    void updatePasswordForm() throws Exception {
        mockMvc.perform(get(ROOT + SETTINGS + PASSWORD))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("passwordForm"));
    }

    @WithAccount("test")
    @DisplayName("비밀번호 수정 - 입력값 정상")
    @Test
    void updatePassword_with_correct_input() throws Exception {
        mockMvc.perform(post(ROOT + SETTINGS + PASSWORD)
                        .param("newPassword", "11112222")
                        .param("newPasswordConfirm", "11112222")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ROOT + SETTINGS + PASSWORD))
                .andExpect(flash().attributeExists("message"));

        Account test = accountRepository.findByUserId("test");
        assertTrue(passwordEncoder.matches("11112222", test.getPassword()));
    }

    @WithAccount("test")
    @DisplayName("비밀번호 수정 - 입력값 오류")
    @Test
    void updatePassword_with_wrong_input() throws Exception {
        mockMvc.perform(post(ROOT + SETTINGS + PASSWORD)
                        .param("newPassword", "11112222")
                        .param("newPasswordConfirm", "11113333")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(ROOT + SETTINGS + PASSWORD))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("passwordForm"));
    }

    @WithAccount("test")
    @DisplayName("닉네임 수정 폼")
    @Test
    void updateAccountForm() throws Exception {
        mockMvc.perform(get(ROOT + SETTINGS + ACCOUNT))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("nicknameForm"));
    }

    @WithAccount("test")
    @DisplayName("닉네임 수정하기 - 입력값 정상")
    @Test
    void updateNickname_with_correct_input() throws Exception {
        String newNickname = "test123";

        mockMvc.perform(post(ROOT + SETTINGS + ACCOUNT)
                        .param("nickname", newNickname)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ROOT + SETTINGS + ACCOUNT))
                .andExpect(flash().attributeExists("message"));

        assertNotNull(accountRepository.findByNickname("test123"));
    }

    @WithAccount("test")
    @DisplayName("닉네임 수정하기 - 입력값 오류")
    @Test
    void updateNickname_with_wrong_input() throws Exception {
        String newNickname = "test!@";

        mockMvc.perform(post(ROOT + SETTINGS + ACCOUNT)
                        .param("nickname", newNickname)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(ROOT + SETTINGS + ACCOUNT))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("nicknameForm"));
    }

}
