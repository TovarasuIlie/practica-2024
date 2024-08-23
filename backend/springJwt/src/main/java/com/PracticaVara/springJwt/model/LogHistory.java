package com.PracticaVara.springJwt.model;

import com.PracticaVara.springJwt.model.Account.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LogHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "action_date", nullable = false)
    private LocalDateTime actionDate;
}
