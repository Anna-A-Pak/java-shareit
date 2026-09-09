package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewBookingDto {
    private Integer itemId;
    @NotNull
    private LocalDateTime start;
    @NotNull
    private LocalDateTime end;

    public NewBookingDto(Integer itemId, LocalDateTime start, LocalDateTime end) {
        this.itemId = itemId;
        this.start = start;
        this.end = end;
    }
}
