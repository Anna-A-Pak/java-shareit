package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class BookingDtoJsonTest {
    @Autowired
    private JacksonTester<BookingDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new BookingDto(
                1,
                new Item(1, "jcfsdhfksd", "csdhfghjrdg", false),
                LocalDateTime.now().minusDays(5).truncatedTo(ChronoUnit.MICROS),
                LocalDateTime.now().minusDays(2).truncatedTo(ChronoUnit.MICROS),
                new User(1, "Name", "name@name.name"),
                BookingStatus.APPROVED);

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.item");
        assertThat(result).hasJsonPath("$.item.id");
        assertThat(result).hasJsonPath("$.item.name");
        assertThat(result).hasJsonPath("$.item.description");
        assertThat(result).hasJsonPath("$.item.available");
        assertThat(result).hasJsonPath("$.start");
        assertThat(result).hasJsonPath("$.end");
        assertThat(result).hasJsonPath("$.booker");
        assertThat(result).hasJsonPath("$.booker.id");
        assertThat(result).hasJsonPath("$.booker.name");
        assertThat(result).hasJsonPath("$.booker.email");
        assertThat(result).hasJsonPath("$.status");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId());
        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(dto.getItem().getId());
        assertThat(result).extractingJsonPathStringValue("$.item.name").isEqualTo(dto.getItem().getName());
        assertThat(result).extractingJsonPathStringValue("$.item.description")
                .isEqualTo(dto.getItem().getDescription());
        assertThat(result).extractingJsonPathBooleanValue("$.item.available")
                .isEqualTo(dto.getItem().getAvailable());
        assertThat(result).extractingJsonPathValue("$.start")
                .isEqualTo(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dto.getStart()));
        assertThat(result).extractingJsonPathValue("$.end")
                .isEqualTo(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dto.getEnd()));
        assertThat(result).extractingJsonPathNumberValue("$.booker.id").isEqualTo(dto.getBooker().getId());
        assertThat(result).extractingJsonPathStringValue("$.booker.name")
                .isEqualTo(dto.getBooker().getName());
        assertThat(result).extractingJsonPathStringValue("$.booker.email")
                .isEqualTo(dto.getBooker().getEmail());
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(dto.getStatus().name());
    }
}
