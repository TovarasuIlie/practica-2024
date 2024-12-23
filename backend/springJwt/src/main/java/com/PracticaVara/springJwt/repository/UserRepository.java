package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String emails);
    Optional<User> findById(Integer id);
    List<User> findAllByOrderByRoleAscIdAsc();
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM users WHERE CAST(users.registered_date AS DATE) = CURRENT_DATE")
    Long countTodayRegistredUsers();

}

