package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;

    @Override
    public ItemDto create(Integer userId, ItemDto itemDto) {
        User user = UserMapper.mapToUser(userService.findById(userId));
        Item item = ItemMapper.matToItem(itemDto);
        return ItemMapper.mapToDto(itemRepository.save(user, item));
    }

    @Override
    public ItemDto update(Integer id, ItemDto updatedItemDto, Integer userId) {
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
        userService.findById(userId);
        Item item = getItem(id);
        Item updatedItem = ItemMapper.matToItem(updatedItemDto);
        if (item.getOwner().getId().equals(userId)) {
            updatedItem.setId(id);
        } else {
            throw new ForbiddenException("Пользователь с id=" + userId + "не является владельцем вещи с id=" + id);
        }
        return ItemMapper.mapToDto(itemRepository.update(updatedItem));
    }

    @Override
    public ItemDto findById(Integer id) {
        return ItemMapper.mapToDto(getItem(id));
    }

    @Override
    public List<ItemDto> findAllByOwner(Integer userId) {
        User user = UserMapper.mapToUser(userService.findById(userId));
        return ItemMapper.mapToListItemDto(itemRepository.findAllByOwner(user));
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        text = text.toLowerCase();
        return ItemMapper.mapToListItemDto(itemRepository.searchItems(text));
    }

    private Item getItem(Integer id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isEmpty()) {
            throw new NotFoundException("Вещь с id = " + id + " не найдена");
        }
        return itemOptional.get();
    }
}
