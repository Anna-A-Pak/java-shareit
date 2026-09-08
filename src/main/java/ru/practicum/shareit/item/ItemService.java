package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDatesDto;

import java.util.List;

public interface ItemService {
    ItemDto create(Integer userId, ItemDto itemDto);

    ItemDto update(Integer id, ItemDto itemDto, Integer userId);

    ItemDto findById(Integer id);

    List<ItemWithDatesDto> findAllByOwner(Integer userId);

    List<ItemDto> searchItems(String text);

    void checkOwner(Item item, Integer userId);

    CommentDto createComment(CommentDto commentDto, Integer itemId, Integer userId);
}
