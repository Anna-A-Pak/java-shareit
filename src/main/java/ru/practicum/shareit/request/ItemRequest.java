package ru.practicum.shareit.request;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
public class ItemRequest {
    @Id
    @Column(name = "id_request", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User requester;
    private LocalDateTime crated;
}
