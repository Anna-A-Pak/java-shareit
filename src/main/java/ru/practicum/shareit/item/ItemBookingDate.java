package ru.practicum.shareit.item;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemBookingDate {
    private Integer itemId;
    private LocalDateTime lastBookingDate;
    private LocalDateTime nextBookingDate;

    public ItemBookingDate(Integer itemId, LocalDateTime lastBookingDate, LocalDateTime nextBookingDate) {
        this.itemId = itemId;
        this.lastBookingDate = lastBookingDate;
        this.nextBookingDate = nextBookingDate;
    }
}
