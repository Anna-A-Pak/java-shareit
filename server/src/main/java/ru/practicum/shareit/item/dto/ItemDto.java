package ru.practicum.shareit.item.dto;


import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemDto {
    private Integer id;
    private User owner;
    private String name;
    private String description;
    private Boolean available;
    private Integer requestId;
    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;
    private List<CommentDto> comments;

}
