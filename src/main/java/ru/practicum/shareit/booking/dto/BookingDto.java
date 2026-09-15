package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Integer id;
    private Item item;
    private LocalDateTime start;
    private LocalDateTime end;
    private User booker;
    private BookingStatus status;

    public BookingDto(Integer id, Item item, LocalDateTime start, LocalDateTime end,
                      User booker, BookingStatus status) {
        this.id = id;
        this.item = item;
        this.start = start;
        this.end = end;
        this.booker = booker;
        this.status = status;
    }
}
