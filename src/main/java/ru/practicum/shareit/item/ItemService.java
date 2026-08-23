package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(Integer userId, ItemDto itemDto);

    ItemDto update(Integer id, ItemDto itemDto, Integer userId);

    ItemDto findById(Integer id);

    List<ItemDto> findAllByOwner(Integer userId);

    List<ItemDto> searchItems(String text);
}
