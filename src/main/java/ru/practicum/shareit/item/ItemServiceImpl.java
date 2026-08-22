package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;

    public ItemServiceImpl(UserService userService,
                           ItemRepository itemRepository) {
        this.userService = userService;
        this.itemRepository = itemRepository;
    }

    @Override
    public Item create(Integer userId, Item item) {
        User user = userService.findById(userId);
        return itemRepository.save(user, item);
    }

    @Override
    public Item update(Integer id, Item updatedItem, Integer userId) {
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
        User user = userService.findById(userId);
        Item item = findById(id);
        if (item.getOwner().getId().equals(userId)) {
            updatedItem.setId(id);
        } else {
            throw new ForbiddenException("Пользователь с id=" + userId + "не является владельцем вещи с id=" + id);
        }
        return itemRepository.update(updatedItem);
    }

    @Override
    public Item findById(Integer id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isEmpty()) {
            throw new NotFoundException("Вещь с id = " + id + " не найдена");
        }
        return itemOptional.get();
    }

    @Override
    public List<Item> findAllByOwner(Integer userId) {
        User user = userService.findById(userId);
        return itemRepository.findAllByOwner(user);
    }

    @Override
    public List<Item> searchItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.searchItems(text);
    }
}
