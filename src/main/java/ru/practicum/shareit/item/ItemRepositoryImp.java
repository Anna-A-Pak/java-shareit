package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImp implements ItemRepository {
    private final Map<Integer, Item> items = new HashMap<>();

    @Override
    public Item save(User user, Item item) {
        item.setId(getNextId());
        item.setOwner(user);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Item item) {
        Item updatedItem = items.get(item.getId());
        if (item.getName() != null) {
            updatedItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            updatedItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            updatedItem.setAvailable(item.getAvailable());
        }
        return updatedItem;
    }

    @Override
    public Optional<Item> findById(Integer id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public List<Item> findAllByOwner(User user) {
        Map<Integer, Item> itemsByOwner = items.entrySet()
                .stream()
                .filter(item -> item.getValue().getOwner().equals(user))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new ArrayList<>(itemsByOwner.values());
    }

    @Override
    public List<Item> searchItems(String text) {
        Map<Integer, Item> searchedItems = items.entrySet().stream()
                .filter(item -> Boolean.TRUE.equals(item.getValue().getAvailable()))
                .filter(item -> item.getValue().getName().toLowerCase().contains(text)
                        || item.getValue().getDescription().toLowerCase().contains(text))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new ArrayList<>(searchedItems.values());
    }

    private int getNextId() {
        int currentMaxId = items.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
