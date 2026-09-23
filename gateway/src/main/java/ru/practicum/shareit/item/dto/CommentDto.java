package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    @NotBlank
    @Size(max = 255)
    private String text;
    private Integer itemId;
    private String authorName;
    private LocalDateTime created;
}
