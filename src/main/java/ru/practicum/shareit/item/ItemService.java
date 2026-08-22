package ru.practicum.shareit.item;

import java.util.List;

public interface ItemService {
    Item create(Integer userId, Item item);

    Item update(Integer id, Item item, Integer userId);

    Item findById(Integer id);

    List<Item> findAllByOwner(Integer userId);

    List<Item> searchItems(String text);
}
