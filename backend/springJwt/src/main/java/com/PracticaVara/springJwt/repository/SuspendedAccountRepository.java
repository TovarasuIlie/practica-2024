package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.SuspendedAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SuspendedAccountRepository extends JpaRepository<SuspendedAccount, Integer> {
    Optional<SuspendedAccount> findSuspendedAccountByUserSuspend(User user);
    Optional<SuspendedAccount> findSuspendedAccountByIpAddress(String ipAddress);
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM suspended_accounts WHERE CAST(suspended_accounts.starting_date AS DATE) = CURRENT_DATE")
    Long countTodaySuspendedAccounts();
}
