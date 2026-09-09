package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDatesDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto create(Integer userId, ItemDto itemDto) {
        User user = UserMapper.mapToUser(userService.findById(userId));
        Item item = ItemMapper.mapToItem(itemDto);
        item.setOwner(user);
        return ItemMapper.mapToDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(Integer id, ItemDto updatedItemDto, Integer userId) {
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
        userService.findById(userId);
        Item item = getItem(id);
        Item updatedItem = ItemMapper.mapToItem(updatedItemDto);
        checkOwner(item, userId);
        return ItemMapper.mapToDto(itemRepository.save(ItemMapper.updatedItem(item, updatedItem)));
    }

    @Override
    public ItemDto findById(Integer id) {
        ItemDto itemDto = ItemMapper.mapToDto(getItem(id));
        List<CommentDto> commentsDto = CommentMapper.mapToListCommentDto(commentRepository.findByItem_Id(id));
        itemDto.setComments(commentsDto);

        return itemDto;
    }

    @Override
    public List<ItemWithDatesDto> findAllByOwner(Integer userId) {
        LocalDateTime timeNow = LocalDateTime.now();
        User user = UserMapper.mapToUser(userService.findById(userId));
        List<ItemWithDatesDto> itemsDto = ItemMapper.mapToListItemWithDateDto(itemRepository.findByOwner(user));
        List<ItemBookingDate> itemBookings = bookingRepository.getBookingDate(user.getId(), timeNow);
        List<CommentDto> commentsDto = CommentMapper.mapToListCommentDto(commentRepository.findByItem_Owner_Id(userId));

        Map<Integer, ItemBookingDate> itemsBookings = itemBookings
                .stream()
                .collect(Collectors.toMap(
                                ItemBookingDate::getItemId,
                                Function.identity()
                ));
        Map<Integer, List<CommentDto>> commentsDtoMap = commentsDto
                .stream()
                .collect(Collectors.groupingBy(CommentDto::getItemId));

        for (ItemWithDatesDto item : itemsDto) {
            if (itemsBookings.containsKey(item.getId())) {
                item.setLastBookingDate(itemsBookings.get(item.getId()).getLastBookingDate());
                item.setNextBookingDate(itemsBookings.get(item.getId()).getNextBookingDate());
            }
            item.setComments(commentsDtoMap.getOrDefault(item.getId(), new ArrayList<>()));
        }

        return itemsDto;
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        text = text.toLowerCase();
        return ItemMapper.mapToListItemDto(itemRepository.searchItems(text));
    }

    @Override
    public void checkOwner(Item item, Integer userId) {
        if (!item.getOwner().getId().equals(userId)) {
            throw new ForbiddenException("Пользователь с id=" +
                    userId + "не является владельцем вещи с id=" + item.getId());
        }
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer itemId, Integer userId) {
        Booking booking = bookingRepository.findByItem_Id(itemId);
        if (booking.getBooker().getId().equals(userId) && booking.getEnd().isBefore(LocalDateTime.now())) {
            commentDto.setItemId(booking.getItem().getId());
            commentDto.setAuthorName(booking.getBooker().getName());
            commentDto.setCreated(LocalDateTime.now());
        } else {
            throw new ValidationException("Операция недоступна");
        }
        return CommentMapper.mapToDto(commentRepository.save(CommentMapper
                .mapToComment(commentDto, booking.getItem(), booking.getBooker())));
    }

    private Item getItem(Integer id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isEmpty()) {
            throw new NotFoundException("Вещь с id = " + id + " не найдена");
        }
        return itemOptional.get();
    }
}
