package com.PracticaVara.springJwt.model;

import com.PracticaVara.springJwt.model.Account.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "suspended_accounts")
public class SuspendedAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @Column(name = "starting_date", nullable = false)
    private LocalDateTime startingDate;

    @Column(name = "ending_date")
    private LocalDateTime endingDate;

    @Column(name = "permanent_suspend", nullable = false)
    private boolean permanentSuspend = false;

    @Column(name = "suspend_reason", nullable = false)
    private String suspendReason;

    @Column(name = "ip_address")
    private String ipAddress;

}
