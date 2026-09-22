package ru.practicum.shareit.request;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "requests")
@Getter
@Setter
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
    private LocalDateTime created;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ItemRequest itemRequest)) return false;

        return id != null && id.equals(itemRequest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
