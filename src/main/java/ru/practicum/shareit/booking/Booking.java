package ru.practicum.shareit.booking;


import lombok.Data;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */

@Data
public class Booking {
    private Integer id;
    private Item item;
    private LocalDate dataStart;
    private LocalDate dataEnd;
    private User booker;
    private BookingStatus status;
}
