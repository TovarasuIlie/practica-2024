package com.PracticaVara.springJwt.model;

import com.PracticaVara.springJwt.model.Account.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ManyToOne
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "is_solved",nullable = false)
    private boolean isSolved = false;

}
