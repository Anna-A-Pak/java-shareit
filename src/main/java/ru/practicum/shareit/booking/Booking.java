package ru.practicum.shareit.booking;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking {
    @Id
    @Column(name = "booking_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @Column(name = "data_start", nullable = false)
    private LocalDateTime start;
    @Column(name = "data_end", nullable = false)
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User booker;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Booking booking)) return false;

        return id != null && id.equals(booking.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
