package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.ResetPasswordCode;
import com.PracticaVara.springJwt.model.Account.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordCodeRepository extends JpaRepository<ResetPasswordCode, Integer> {
    Optional<ResetPasswordCode> findByCode(String code);
    Optional<ResetPasswordCode> findByUser(User user);
}
