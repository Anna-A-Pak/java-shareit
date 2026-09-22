package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemClient itemClient;
    public static final String USER_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ItemDto itemDto,
                                         @RequestHeader(USER_HEADER) Integer userId) {
        return itemClient.create(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Integer id,
                                          @RequestBody ItemDto itemDto,
                                          @RequestHeader(USER_HEADER) Integer userId) {
        return itemClient.update(userId, itemDto, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@PathVariable Integer id) {
        return itemClient.getItem(id);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsByOwner(@RequestHeader(USER_HEADER) Integer userId) {
        return itemClient.getItemsByOwner(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestParam String text) {
        return itemClient.searchItems(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestBody CommentDto commentDto,
                                                @PathVariable Integer itemId,
                                                @RequestHeader(USER_HEADER) Integer userId) {
        return itemClient.createComment(commentDto, itemId, userId);
    }
}
