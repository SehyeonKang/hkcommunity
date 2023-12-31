package com.hkcommunity.modules.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);

    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);

    Account findByUserId(String userId);

    Account findByEmail(String email);

    Account findByNickname(String nickname);
}
