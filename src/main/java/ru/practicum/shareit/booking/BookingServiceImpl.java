package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserService userService;
    private final ItemService itemService;
    private final BookingRepository bookingRepository;

    @Override
    public BookingDto create(Integer userId, NewBookingDto newBookingDto) {
        User user = UserMapper.mapToUser(userService.findById(userId));
        Item item = ItemMapper.mapToItem(itemService.findById(newBookingDto.getItemId()));
        if (!item.getAvailable()) {
            throw new ValidationException("Вещь с id = " + item.getId() + "недоступна к бронированию");
        }
        Booking booking = BookingMapper.mapToBooking(newBookingDto, item, user);
        booking.setStatus(BookingStatus.WAITING);
        return BookingMapper.mapToDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto prove(Integer id, Boolean approved, Integer userId) {
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Бронирование с id = " + id + " не найдено"));
        itemService.checkOwner(booking.getItem(), userId);

        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }

        return BookingMapper.mapToDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto findById(Integer id, Integer userId) {
        if (id == null) {
            throw new ValidationException("Id должен быть указан");
        }
        User user = UserMapper.mapToUser(userService.findById(userId));
        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Бронирование с id = " + id + " не найдено"));
        Item item = booking.getItem();
        if (!item.getOwner().equals(user)) {
            if (!booking.getBooker().equals(user)) {
                throw new ForbiddenException("Пользователю с id=" + userId + "недоступна данная операция");
            }
        }
        return BookingMapper.mapToDto(booking);
    }

    @Override
    public List<BookingDto> findByBooker(String state, Integer userId) {
        LocalDateTime timeNow = LocalDateTime.now();
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        userService.findById(userId);
        List<Booking> bookings = new ArrayList<>();

        if (BookingState.PAST.name().equals(state)) {
            bookings = bookingRepository.findByBooker_IdAndEndIsBefore(
                    userId,
                    timeNow,
                    sort
            );
        }
        if (BookingState.CURRENT.name().equals(state)) {
            bookings = bookingRepository.findByBooker_IdAndStartIsBeforeAndEndIsAfter(
                    userId,
                    timeNow,
                    timeNow,
                    sort
            );
        }
        if (BookingState.FUTURE.name().equals(state)) {
            bookings = bookingRepository.findByBooker_IdAndStartIsAfter(
                    userId,
                    timeNow,
                    sort
            );
        }
        if (BookingStatus.WAITING.name().equals(state)) {
            bookings = bookingRepository.findByBooker_IdAndStatus(
                    userId,
                    BookingStatus.WAITING,
                    sort
            );
        }
        if (BookingStatus.REJECTED.name().equals(state)) {
            bookings = bookingRepository.findByBooker_IdAndStatus(
                    userId,
                    BookingStatus.REJECTED,
                    sort
            );
        }
        if (BookingState.ALL.name().equals(state)) {
            bookings = bookingRepository.findByBooker_Id(userId, sort);
        }
        return BookingMapper.mapToListBookingDto(bookings);
    }

    @Override
    public List<BookingDto> findByOwner(String state, Integer userId) {
        LocalDateTime timeNow = LocalDateTime.now();
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        userService.findById(userId);
        List<Booking> bookings = new ArrayList<>();

        if (BookingState.PAST.name().equals(state)) {
            bookings = bookingRepository.findByItem_Owner_IdAndEndIsBefore(
                    userId,
                    timeNow,
                    sort
            );
        }
        if (BookingState.CURRENT.name().equals(state)) {
            bookings = bookingRepository.findByItem_Owner_IdAndStartIsBeforeAndEndIsAfter(
                    userId,
                    timeNow,
                    timeNow,
                    sort
            );
        }
        if (BookingState.FUTURE.name().equals(state)) {
            bookings = bookingRepository.findByItem_Owner_IdAndStartIsAfter(
                    userId,
                    timeNow,
                    sort
            );
        }
        if (BookingStatus.WAITING.name().equals(state)) {
            bookings = bookingRepository.findByItem_Owner_IdAndStatus(
                    userId,
                    BookingStatus.WAITING,
                    sort
            );
        }
        if (BookingStatus.REJECTED.name().equals(state)) {
            bookings = bookingRepository.findByItem_Owner_IdAndStatus(
                    userId,
                    BookingStatus.REJECTED,
                    sort
            );
        }
        if (BookingState.ALL.name().equals(state)) {
            bookings = bookingRepository.findByItem_Owner_Id(userId, sort);
        }
        return BookingMapper.mapToListBookingDto(bookings);
    }
}
