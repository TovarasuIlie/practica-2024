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
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "createdDate")
    private LocalDateTime createdDate;
    @Column(name = "expirationDate")
    private LocalDateTime expirationDate;
    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "photo_number")
    private int photoNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_deactivated")
    private boolean isDeactivated = false;

    @Column(name = "is_approved")
    private boolean isApproved = false;

}
