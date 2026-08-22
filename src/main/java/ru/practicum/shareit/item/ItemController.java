package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;


@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    public static final String USER_HEADER = "X-Sharer-User-Id";

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemDto> create(@Valid @RequestBody ItemDto itemDto,
                                          @RequestHeader(USER_HEADER) Integer userId) {
        Item item = ItemMapper.matToItem(itemDto);
        Item createdItem = itemService.create(userId, item);
        return ResponseEntity.ok(ItemMapper.mapToDto(createdItem));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> update(@Valid @PathVariable Integer id,
                                          @RequestBody ItemDto itemDto,
                                          @RequestHeader(USER_HEADER) Integer userId) {
        Item item = ItemMapper.matToItem(itemDto);
        return ResponseEntity.ok(ItemMapper.mapToDto(itemService.update(id, item, userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable Integer id) {
        return ResponseEntity.ok(ItemMapper.mapToDto(itemService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItemsByOwner(@RequestHeader(USER_HEADER) Integer userId) {
        List<Item> items = itemService.findAllByOwner(userId);
        return ResponseEntity.ok(ItemMapper.mapToListItemDto(items));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam String text) {
        List<Item> items = itemService.searchItems(text);
        return ResponseEntity.ok(ItemMapper.mapToListItemDto(items));
    }
}
