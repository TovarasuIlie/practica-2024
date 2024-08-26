package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogHistoryRepository extends JpaRepository<LogHistory, Integer> {
    List<LogHistory> findAllByUser(User user);
    @Query(nativeQuery = true, value = "SELECT * FROM log_history LIMIT 100")
    List<LogHistory> last100Actions();
}
