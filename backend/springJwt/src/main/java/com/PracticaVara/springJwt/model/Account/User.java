package com.PracticaVara.springJwt.model.Account;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.NonNull;
=======
import lombok.Getter;
import lombok.Setter;
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
<<<<<<< HEAD
@NoArgsConstructor
@AllArgsConstructor
=======
@Setter
@Getter
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email_verified", columnDefinition = "bit(1) default 0", nullable = false)
    private boolean emailVerifed;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "registered_date")
    private LocalDateTime registeredDate;

    @Column(name = "address")
    private String address;

    @Column(name = "ip_address")
    private String ipAddress;



    @Transient
    private String jwt;

<<<<<<< HEAD

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
=======
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

<<<<<<< HEAD
=======
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
>>>>>>> 530ca60b0d74dabf86a139d435838508ad43e13e
}
