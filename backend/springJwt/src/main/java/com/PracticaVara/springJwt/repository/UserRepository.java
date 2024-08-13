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

//    @Query(value = "SELECT users.*, ip_logs.used_from FROM users JOIN ip_logs ON users.id = ip_logs.user_id WHERE users.id = :id", nativeQuery = true)
//    User findByIdJoinIpLogs(Integer id);
//
//    Optional<User> findByIdAndIPLogsUserEqual(Integer id);



}

