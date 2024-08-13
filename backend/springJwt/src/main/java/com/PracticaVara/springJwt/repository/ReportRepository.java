package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.PracticaVara.springJwt.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findAllByUserOrderBySolvedAscIdDesc(User user); //findAllByUserOrderByIsSolvedAscIdDesc(User user);  ---> sa pui asta cu ...IsSolved... la mijloc daca nu merge ca nu stiu ce Doamne are asta
    Optional<Report> findById(Integer id);
    List<Report> findAll();
    List<Report> findAllByAnnouncementOrderBySolvedAscIdDesc(Announcement announcement);

}
