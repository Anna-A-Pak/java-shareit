package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public class ItemMapper {
    public static ItemDto mapToDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    public static Item matToItem(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable()
        );
    }

    public static List<ItemDto> mapToListItemDto(List<Item> items) {
        return items
                .stream()
                .map(ItemMapper::mapToDto)
                .toList();
    }
}
