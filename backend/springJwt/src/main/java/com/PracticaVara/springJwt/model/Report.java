package com.PracticaVara.springJwt.model;

import com.PracticaVara.springJwt.model.Account.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "is_solved",nullable = false)
    private boolean solved = false;

}
