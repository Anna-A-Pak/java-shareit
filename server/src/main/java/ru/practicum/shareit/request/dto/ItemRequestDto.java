package ru.practicum.shareit.request.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemRequestDto {
    private Integer id;
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
