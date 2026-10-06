package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.request.dto.RequestWithItemsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class RequestWithItemsDtoJsonTest {

    @Autowired
    private JacksonTester<RequestWithItemsDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new RequestWithItemsDto();
        dto.setId(1);
        dto.setDescription("jsdfhjds");
        dto.setCreated(LocalDateTime.now().minusDays(10).truncatedTo(ChronoUnit.MICROS));
        dto.setItems(new ArrayList<>());

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.created");
        assertThat(result).hasJsonPath("$.items");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.created")
                .isEqualTo(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dto.getCreated()));
        assertThat(result).extractingJsonPathArrayValue("$.items").isEqualTo(dto.getItems());
    }

    @Test
    void testSerializeWithNull() throws Exception {
        var dto = new RequestWithItemsDto();
        dto.setId(null);
        dto.setDescription("jsdfhjds");
        dto.setCreated(LocalDateTime.now().minusDays(10).truncatedTo(ChronoUnit.MICROS));
        dto.setItems(new ArrayList<>());

        var result = json.write(dto);
        assertThat(result).doesNotHaveJsonPath("$.id");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.created");
        assertThat(result).hasJsonPath("$.items");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathValue("$.created")
                .isEqualTo(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dto.getCreated()));
        assertThat(result).extractingJsonPathArrayValue("$.items").isEqualTo(dto.getItems());
    }

}
