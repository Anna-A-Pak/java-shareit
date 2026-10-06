package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemWithDatesDto;
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
public class ItemServiceImplTest {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    private User user1;
    private User user2;
    private User user3;
    private Item item1;

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
    }

    @Test
    public void findAllByOwner_shouldReturnItemsWithBookingsAndComments() {
        // given
        LocalDateTime past = LocalDateTime.now().minusDays(4).truncatedTo(ChronoUnit.MICROS);
        LocalDateTime future = LocalDateTime.now().plusDays(2).truncatedTo(ChronoUnit.MICROS);

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item1.getId());
        bookingDto.setStart(past);
        bookingDto.setEnd(past.plusDays(2));
        Booking booking1 = BookingMapper.mapToBooking(bookingDto, item1, user2);
        booking1.setStatus(BookingStatus.APPROVED);

        NewBookingDto bookingDto2 = new NewBookingDto();
        bookingDto2.setItemId(item1.getId());
        bookingDto2.setStart(future);
        bookingDto2.setEnd(future.plusDays(2));
        Booking booking2 = BookingMapper.mapToBooking(bookingDto2, item1, user3);
        booking2.setStatus(BookingStatus.APPROVED);

        CommentDto commentDto = new CommentDto(
                null,
                "Без нареканий",
                null,
                null,
                past.plusDays(3));


        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        commentRepository.save(CommentMapper.mapToComment(commentDto, item1, user2));

        //when
        List<ItemWithDatesDto> result = itemService.findAllByOwner(user1.getId());

        //then
        assertEquals(1, result.size());

        ItemWithDatesDto dto = result.getFirst();

        assertEquals(item1.getId(), dto.getId());
        assertEquals(item1.getName(), dto.getName());
        assertEquals(past, dto.getLastBookingDate());
        assertEquals(future, dto.getNextBookingDate());
        assertEquals(1, dto.getComments().size());
        assertEquals("Без нареканий", dto.getComments().getFirst().getText());
    }

}
