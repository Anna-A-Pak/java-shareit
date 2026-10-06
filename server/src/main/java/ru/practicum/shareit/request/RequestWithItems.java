package ru.practicum.shareit.request;

import lombok.Data;
import ru.practicum.shareit.item.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RequestWithItems {
    private Integer id;
    private String description;
    private LocalDateTime created;
    private List<Item> items = new ArrayList<>();

    public RequestWithItems(Integer id, String description, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.created = created;
    }
}
