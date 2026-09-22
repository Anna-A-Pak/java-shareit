package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookingService bookingService;

    @Autowired
    private ObjectMapper mapper;

    public static final String USER_HEADER = "X-Sharer-User-Id";

    User user = new User(1, "sdjfhjssdjhsd", "hsdff@dgvjdf.ru");
    Item item = new Item(1, "shfgsed", "asdeshjfsd", true);


    private BookingDto dto = new BookingDto(
            1,
            item,
            LocalDateTime.parse("2026-09-22T14:05:29"),
            LocalDateTime.parse("2026-09-23T14:05:29"),
            user,
            BookingStatus.WAITING);

    @Test
    public void testCreateBooking() throws Exception {
        mapper.registerModule(new JavaTimeModule());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(1);
        bookingDto.setStart(LocalDateTime.parse("2026-09-22T14:05:29"));
        bookingDto.setEnd(LocalDateTime.parse("2026-09-23T14:05:29"));

        when(bookingService.create(eq(1), any(NewBookingDto.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/bookings")
                        .header(USER_HEADER, 1)
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.item.id").value(item.getId()))
                .andExpect(jsonPath("$.start").value("2026-09-22T14:05:29"))
                .andExpect(jsonPath("$.end").value("2026-09-23T14:05:29"))
                .andExpect(jsonPath("$.booker.id").value(user.getId()))
                .andExpect(jsonPath("$.status").value("WAITING"));
    }

    @Test
    public void testProveBooking() throws Exception {
        when(bookingService.prove(1, true, 2))
                .thenReturn(dto);

        mockMvc.perform(patch("/bookings/1")
                        .param("approved", "true")
                        .header(USER_HEADER, 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.status").value(dto.getStatus().name()));
    }

    @Test
    public void testGetRequest() throws Exception {
        when(bookingService.findById(1, 1))
                .thenReturn(dto);

        mockMvc.perform(get("/bookings/1")
                        .header(USER_HEADER, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.item.id").value(item.getId()))
                .andExpect(jsonPath("$.start").value("2026-09-22T14:05:29"))
                .andExpect(jsonPath("$.end").value("2026-09-23T14:05:29"))
                .andExpect(jsonPath("$.booker.id").value(user.getId()))
                .andExpect(jsonPath("$.status").value("WAITING"));
    }

    @Test
    public void testFindByBooker() throws Exception {
        when(bookingService.findByBooker("ALL", 1))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/bookings")
                        .param("state", "ALL")
                        .header(USER_HEADER, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].item.id").value(item.getId()))
                .andExpect(jsonPath("$[0].start").value("2026-09-22T14:05:29"))
                .andExpect(jsonPath("$[0].end").value("2026-09-23T14:05:29"))
                .andExpect(jsonPath("$[0].booker.id").value(user.getId()))
                .andExpect(jsonPath("$[0].status").value("WAITING"));
    }

    @Test
    public void testFindByOwner() throws Exception {
        when(bookingService.findByOwner("ALL", 1))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/bookings/owner")
                        .param("state", "ALL")
                        .header(USER_HEADER, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].item.id").value(item.getId()))
                .andExpect(jsonPath("$[0].start").value("2026-09-22T14:05:29"))
                .andExpect(jsonPath("$[0].end").value("2026-09-23T14:05:29"))
                .andExpect(jsonPath("$[0].booker.id").value(user.getId()))
                .andExpect(jsonPath("$[0].status").value("WAITING"));
    }
}
