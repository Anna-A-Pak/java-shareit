package ru.practicum.shareit.request.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemRequestDto {
    private Integer id;
    @NotBlank
    @Size(max = 200)
    private String description;
    private LocalDateTime created;

    public ItemRequestDto(String description) {
        this.description = description;
    }

    public ItemRequestDto(Integer id, String description, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.created = created;
    }
}
