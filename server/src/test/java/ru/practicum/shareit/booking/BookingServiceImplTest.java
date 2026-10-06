package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("test")
public class BookingServiceImplTest {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    private User user1;
    private User user2;
    private User user3;
    private Item item1;
    private Item item2;

    @BeforeEach
    public void beforeEach() {

        user1 = userRepository.save(new User(null, "Name", "name@mail.mail"));
        user2 = userRepository.save(new User(null, "Ivan", "ivan@mail.ru"));
        user3 = userRepository.save(new User(null, "Irina", "irina@mail.mail"));

        item1 = itemRepository.save(new Item(
                null,
                user1,
                "Палатка",
                "2-х местная",
                true,
                null));

        item2 = itemRepository.save(new Item(
                null,
                user3,
                "Палатка",
                "3-х местная",
                true,
                null));
    }

    @Test
    public void findByBooker_shouldReturnWaitingBooking() {
        // given
        LocalDateTime start = LocalDateTime.now().plusDays(4).truncatedTo(ChronoUnit.MICROS);

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item1.getId());
        bookingDto.setStart(start);
        bookingDto.setEnd(start.plusDays(2));
        Booking booking1 = BookingMapper.mapToBooking(bookingDto, item1, user2);
        booking1.setStatus(BookingStatus.WAITING);

        NewBookingDto bookingDto2 = new NewBookingDto();
        bookingDto2.setItemId(item2.getId());
        bookingDto2.setStart(start);
        bookingDto2.setEnd(start.plusDays(2));
        Booking booking2 = BookingMapper.mapToBooking(bookingDto2, item1, user2);
        booking2.setStatus(BookingStatus.APPROVED);

        bookingRepository.saveAll(List.of(booking1, booking2));

        //when
        List<BookingDto> result = bookingService.findByBooker("WAITING", user2.getId());
        BookingDto dto = result.getFirst();

        //then
        assertEquals(1, result.size());
        assertEquals(item1, dto.getItem());
        assertEquals("WAITING", dto.getStatus().name());
    }
}
