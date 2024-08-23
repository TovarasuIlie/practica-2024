package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogHistoryRepository extends JpaRepository<LogHistory, Integer> {
    List<LogHistory> findAllByUser(User user);
}
