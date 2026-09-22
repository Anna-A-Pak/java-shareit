package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.shareit.item.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RequestWithItemsDto {
    private Integer id;
    @NotBlank
    @Size(max = 200)
    private String description;
    private LocalDateTime created;
    private List<Item> items = new ArrayList<>();

    public RequestWithItemsDto(String description, List<Item> items) {
        this.description = description;
        this.items = items;
    }

    public RequestWithItemsDto() {
    }
}
