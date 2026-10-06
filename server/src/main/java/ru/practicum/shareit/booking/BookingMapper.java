package ru.practicum.shareit.booking;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.util.List;


@UtilityClass
public class BookingMapper {
    public static BookingDto mapToDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getItem(),
                booking.getStart(),
                booking.getEnd(),
                booking.getBooker(),
                booking.getStatus()
        );
    }

    public static Booking mapToBooking(NewBookingDto newBookingDto, Item item, User user) {
        Booking booking = new Booking();
        booking.setItem(item);
        booking.setStart(newBookingDto.getStart());
        booking.setEnd(newBookingDto.getEnd());
        booking.setBooker(user);

        return booking;
    }

    public static List<BookingDto> mapToListBookingDto(List<Booking> bookings) {
        return bookings
                .stream()
                .map(BookingMapper::mapToDto)
                .toList();
    }
}
