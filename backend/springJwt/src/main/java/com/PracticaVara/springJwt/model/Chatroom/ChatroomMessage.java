package com.PracticaVara.springJwt.model.Chatroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chatroom_messages")
public class ChatroomMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "chatroom_id", nullable = false, referencedColumnName = "id")
    @JsonManagedReference
    @JsonIgnore
    private Chatroom chatroom;

    @Column(name = "sender_id")
    private Integer sender;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "send_at", nullable = false)
    private LocalDateTime sendAt;
}
