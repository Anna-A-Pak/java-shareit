package ru.practicum.shareit.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewBookingDto {
    private Integer itemId;
    private LocalDateTime start;
    private LocalDateTime end;

    public NewBookingDto(Integer itemId, LocalDateTime start, LocalDateTime end) {
        this.itemId = itemId;
        this.start = start;
        this.end = end;
    }
}
