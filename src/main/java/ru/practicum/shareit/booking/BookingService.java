package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;

import java.util.List;

public interface BookingService {
    BookingDto create(Integer userId, NewBookingDto newBookingDto);

    BookingDto prove(Integer id, Boolean approved, Integer userId);

    BookingDto findById(Integer id, Integer userId);

    List<BookingDto> findByBooker(String state, Integer userId);

    List<BookingDto> findByOwner(String state, Integer userId);
}
