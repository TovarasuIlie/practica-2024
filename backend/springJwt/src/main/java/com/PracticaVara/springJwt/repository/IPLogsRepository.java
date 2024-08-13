package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.IPLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPLogsRepository  extends JpaRepository<IPLogs, Integer> {

}
