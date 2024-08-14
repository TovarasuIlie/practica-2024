package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.SuspendedAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuspendedAccountRepository extends JpaRepository<SuspendedAccount, Integer> {
    Optional<SuspendedAccount> findSuspendedAccountByUser(User user);
    Optional<SuspendedAccount> findSuspendedAccountByIpAddress(String ipAddress);
}
