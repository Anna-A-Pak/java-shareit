package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Integer id;
    @NotBlank
    private String text;
    private Integer itemId;
    private String authorName;
    private LocalDateTime created;

    public CommentDto(Integer id, String text, Integer itemId, String authorName, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.itemId = itemId;
        this.authorName = authorName;
        this.created = created;
    }
}
