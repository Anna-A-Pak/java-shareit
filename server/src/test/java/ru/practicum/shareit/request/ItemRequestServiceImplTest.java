package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestWithItemsDto;
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
public class ItemRequestServiceImplTest {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemRequestRepository requestRepository;
    private final ItemRequestService requestService;

    private User user1;
    private User user2;
    private User user3;
    private Item item1;
    private Item item2;
    private ItemRequest itemRequest1;
    private ItemRequest itemRequest2;

    @BeforeEach
    public void beforeEach() {

        user1 = userRepository.save(new User(null, "Name", "name@mail.mail"));
        user2 = userRepository.save(new User(null, "Ivan", "ivan@mail.ru"));
        user3 = userRepository.save(new User(null, "Irina", "irina@mail.mail"));


    }

    @Test
    public void findAllByRequester_shouldReturnRequestsWithItemsAndWithoutItems() {
        // given
        LocalDateTime created1 = LocalDateTime.now().minusHours(1).truncatedTo(ChronoUnit.MICROS);
        LocalDateTime created2 = LocalDateTime.now().minusHours(3).truncatedTo(ChronoUnit.MICROS);

        ItemRequestDto dto1 = new ItemRequestDto(null, "Палатка", created1);
        ItemRequestDto dto2 = new ItemRequestDto(null, "Камера", created2);

        itemRequest1 = ItemRequestMapper.mapToItemRequest(dto1, user1);
        itemRequest1.setCreated(created1);
        itemRequest1 = requestRepository.save(itemRequest1);
        itemRequest2 = ItemRequestMapper.mapToItemRequest(dto2, user1);
        itemRequest2.setCreated(created2);
        itemRequest2 = requestRepository.save(itemRequest2);

        item1 = itemRepository.save(new Item(
                null,
                user2,
                "Палатка",
                "2-х местная",
                true,
                itemRequest1));

        item2 = itemRepository.save(new Item(
                null,
                user3,
                "Палатка",
                "3-х местная",
                true,
                itemRequest1));

        //when
        List<RequestWithItemsDto> result = requestService.findAllByRequester(user1.getId());

        //then
        assertEquals(2, result.size());

        RequestWithItemsDto requestDto1 = result.getFirst();
        RequestWithItemsDto requestDto2 = result.getLast();

        assertEquals(2, requestDto1.getItems().size());
        assertEquals(0, requestDto2.getItems().size());
        assertEquals(item1, requestDto1.getItems().getFirst());
        assertEquals(item2, requestDto1.getItems().getLast());
    }
}
