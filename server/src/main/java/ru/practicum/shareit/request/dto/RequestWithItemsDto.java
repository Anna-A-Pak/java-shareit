package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestWithItemsDto {
    private Integer id;
    private String description;
    private LocalDateTime created;
    private List<Item> items = new ArrayList<>();

    public RequestWithItemsDto(String description, List<Item> items) {
        this.description = description;
        this.items = items;
    }

}
