package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDatesDto;

import java.util.List;

@UtilityClass
public class ItemMapper {
    public static ItemDto mapToDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    public static Item mapToItem(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable()
        );
    }

    public static ItemWithDatesDto mapToItemWithDateDto(Item item) {
        return new ItemWithDatesDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    public static List<ItemDto> mapToListItemDto(List<Item> items) {
        return items
                .stream()
                .map(ItemMapper::mapToDto)
                .toList();
    }

    public static List<ItemWithDatesDto> mapToListItemWithDateDto(List<Item> items) {
        return items
                .stream()
                .map(ItemMapper::mapToItemWithDateDto)
                .toList();
    }

    public static Item updatedItem(Item item, Item updateItem) {
        updateItem.setId(item.getId());
        updateItem.setOwner(item.getOwner());
        if (updateItem.getName() == null || updateItem.getName().isBlank()) {
            updateItem.setName(item.getName());
        }
        if (updateItem.getDescription() == null || updateItem.getDescription().isBlank()) {
            updateItem.setDescription(item.getDescription());
        }
        if (updateItem.getAvailable() == null) {
            updateItem.setAvailable(item.getAvailable());
        }
        updateItem.setRequest(item.getRequest());
        return updateItem;
    }
}
