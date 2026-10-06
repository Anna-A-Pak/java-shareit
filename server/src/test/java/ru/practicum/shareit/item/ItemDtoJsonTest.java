package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class ItemDtoJsonTest {

    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    void testSerializeWithNull() throws Exception {
        var dto = new ItemDto();
        dto.setId(1);
        dto.setOwner(new User(1, "Name", "name@name.name"));
        dto.setName("vsdkjgd");
        dto.setDescription("sdjkfesjkrsdfjkg");
        dto.setAvailable(true);
        dto.setRequestId(null);
        dto.setLastBooking(null);
        dto.setNextBooking(null);
        dto.setComments(null);

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.owner");
        assertThat(result).hasJsonPath("$.owner.id");
        assertThat(result).hasJsonPath("$.owner.name");
        assertThat(result).hasJsonPath("$.owner.email");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.available");
        assertThat(result).hasJsonPath("$.requestId");
        assertThat(result).hasJsonPath("$.lastBooking");
        assertThat(result).hasJsonPath("$.nextBooking");
        assertThat(result).hasJsonPath("$.comments");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId());
        assertThat(result).extractingJsonPathNumberValue("$.owner.id").isEqualTo(dto.getOwner().getId());
        assertThat(result).extractingJsonPathStringValue("$.owner.name").isEqualTo(dto.getOwner().getName());
        assertThat(result).extractingJsonPathStringValue("$.owner.email").isEqualTo(dto.getOwner().getEmail());
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(dto.getAvailable());
        assertThat(result).extractingJsonPathStringValue("$.requestId").isNull();
        assertThat(result).extractingJsonPathStringValue("$.lastBooking").isNull();
        assertThat(result).extractingJsonPathStringValue("$.nextBooking").isNull();
        assertThat(result).extractingJsonPathBooleanValue("$.comments").isNull();
    }
}
