package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.RequestHeaders;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDatesDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> create(@RequestBody ItemDto itemDto,
                                          @RequestHeader(RequestHeaders.USER_HEADER) Integer userId) {
        return ResponseEntity.ok(itemService.create(userId, itemDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> update(@PathVariable Integer id,
                                          @RequestBody ItemDto itemDto,
                                          @RequestHeader(RequestHeaders.USER_HEADER) Integer userId) {
        return ResponseEntity.ok((itemService.update(id, itemDto, userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable Integer id) {
        return ResponseEntity.ok((itemService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ItemWithDatesDto>> getItemsByOwner(
            @RequestHeader(RequestHeaders.USER_HEADER) Integer userId) {
        return ResponseEntity.ok(itemService.findAllByOwner(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam String text) {
        return ResponseEntity.ok(itemService.searchItems(text));
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Integer itemId,
                                                    @RequestHeader(RequestHeaders.USER_HEADER) Integer userId) {
        return ResponseEntity.ok(itemService.createComment(commentDto, itemId, userId));
    }
}
