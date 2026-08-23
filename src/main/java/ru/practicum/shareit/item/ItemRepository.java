package ru.practicum.shareit.item;

import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(User user, Item item);

    Item update(Item item);

    Optional<Item> findById(Integer id);

    List<Item> findAllByOwner(User user);

    List<Item> searchItems(String text);
}
