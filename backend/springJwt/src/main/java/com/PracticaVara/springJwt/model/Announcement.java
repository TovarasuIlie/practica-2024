package com.PracticaVara.springJwt.model;

import com.PracticaVara.springJwt.model.Account.User;
import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
=======
import lombok.Getter;
import lombok.Setter;
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "expirationDate", nullable = false)
    private LocalDateTime expirationDate;
    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;
    @Column(name = "photo_number", nullable = false)
    private int photoNumber;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "is_deactivated", nullable = false)
    private boolean isDeactivated = false;
<<<<<<< HEAD

    @Column(name = "is_approved")
    private boolean isApproved = false;
=======
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "contact_person_name", nullable = false)
    private String contactPersonName;
    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;
    @Column(name = "address", nullable = false)
    private String address;
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e

}
