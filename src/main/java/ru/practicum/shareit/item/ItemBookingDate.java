package ru.practicum.shareit.item;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemBookingDate {
    private final Integer itemId;
    private final LocalDateTime lastBookingDate;
    private final LocalDateTime nextBookingDate;

    public ItemBookingDate(Integer itemId, LocalDateTime lastBookingDate, LocalDateTime nextBookingDate) {
        this.itemId = itemId;
        this.lastBookingDate = lastBookingDate;
        this.nextBookingDate = nextBookingDate;
    }
}
