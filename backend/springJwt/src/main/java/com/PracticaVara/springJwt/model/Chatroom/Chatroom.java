package com.PracticaVara.springJwt.model.Chatroom;

import com.PracticaVara.springJwt.model.Account.User;
import com.PracticaVara.springJwt.model.Announcement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chatrooms")
public class Chatroom {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    @OneToOne
    @JoinColumn(name = "announcement_id",  nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties(allowSetters = true, value = { "content", "createDate", "expirationDate", "photoNumber", "phoneNumber", "address", "approved", "deactivated", "user" })
    private Announcement announcement;

    @OneToOne
    @JoinColumn(name = "seller_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties(allowSetters = true, value = { "ipLogs", "password", "jwt", "ipAddress", "address", "registeredDate", "suspendDetails", "enabled", "authorities", "role", "emailVerifed", "email", "accountNonLocked", "accountNonExpired", "credentialsNonExpired" })
    private User seller;

    @OneToOne
    @JoinColumn(name = "buyer_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties(allowSetters = true, value = { "ipLogs", "password", "jwt", "ipAddress", "address", "registeredDate", "suspendDetails", "enabled", "authorities", "role", "emailVerifed", "email", "accountNonLocked", "accountNonExpired", "credentialsNonExpired" })
    private User buyer;

    @Column(name = "create_date")
    private LocalDateTime createDate;
}
